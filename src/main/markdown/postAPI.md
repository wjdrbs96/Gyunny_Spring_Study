# Post Table

| postIdx | authors | title | createdAt | memberIdx |
|----|----|-----|----|----|
  | 게시글 인덱스 | 저자 | 제목 | 작성시간 | 작성자 인덱스 |


<br>

### 게시글 전체조회, 하나조회, 게시글등록, 게시글 수정, 게시글삭제 API 만들기

|Method|URL| 내용 |
|------|---| --- |
|GET|/posts| 게시글 전체조회 |
|GET|/posts/{idx}| 게시글 하나조회 | 
|POST|/posts| 게시글 등록 |
|PUT|/posts/{idx}| 게시글 수정(Token을 이용하여 등록한 사람만 수정 가능) |
|DELETE|/posts/{idx}| 게시글 삭제(Token을 이용하여 등록한 사람만 삭제 가능) |