package com.example.demo.controller;

import com.example.demo.model.DefaultRes;
import com.example.demo.model.ProfileModel;
import com.example.demo.service.ProfileService;
import com.example.demo.utils.ResponseMessage;
import com.example.demo.utils.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("profile")
public class ProfileController {

    public static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/save")
    public ResponseEntity saveProfile(ProfileModel profileModel, @RequestPart(value = "profile", required = false) final MultipartFile multipartFile) {
        try {
            return new ResponseEntity(profileService.profileSave(profileModel, multipartFile), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
