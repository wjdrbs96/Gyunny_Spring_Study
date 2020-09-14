package com.example.demo.service;

import com.example.demo.mapper.ProfileMapper;
import com.example.demo.model.DefaultRes;
import com.example.demo.model.ProfileModel;
import com.example.demo.utils.ResponseMessage;
import com.example.demo.utils.StatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileService {

    private final S3FileUploadService s3FileUploadService;
    private final ProfileMapper profileMapper;

    public ProfileService(S3FileUploadService s3FileUploadService, ProfileMapper profileMapper) {
        this.s3FileUploadService = s3FileUploadService;
        this.profileMapper = profileMapper;
    }


    public DefaultRes profileSave(ProfileModel profileModel, MultipartFile multipartFile) {
        try {
            if (multipartFile != null) {
                profileModel.setProfileURL(s3FileUploadService.upload(multipartFile));
            }

            profileMapper.saveProfile(profileModel);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.SUCCESS_PROFILE_REGISTER);
        } catch (Exception e) {
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }
}
