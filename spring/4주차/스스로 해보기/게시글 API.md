# Post Table

| postIdx | authors | title | createdAt | memberIdx |
|----|----|-----|----|----|
  | 게시글 인덱스 | 저자 | 제목 | 작성시간 | 작성자 인덱스 |

- 자유롭게~~

<br>

### `POST CRUD` 

| Method | URL | 내용 |
|------|---| --- |
|GET|/posts| 게시글 전체조회 |
|GET|/posts/{idx}| 게시글 하나조회 | 
|POST|/posts| 게시글 등록 |
|PUT|/posts/{idx}| 게시글 수정 |
|DELETE|/posts/{idx}| 게시글 삭제 |