# 1. 프로젝트 소개
Feel Pick은 사용자의 기분 상태와 추가 사항을 입력받아 AI가 그에 맞는 음식을 추천해주는 서비스입니다. "지금 기분은 어떤가요?" 라는 질문에 솔직하게 털어놓고, 음식이 주는 위로를 경험해 보세요!

<br/>

# 2. 개발 환경
### [FrontEnd]
<img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white" alt="HTML5" /> <img src="https://img.shields.io/badge/CSS-1572B6?style=for-the-badge&logo=css3&logoColor=white" alt="CSS3" /> 
<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black" alt="JavaScript" />
  
### [BackEnd]
<img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white" alt="Java" /> <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring" />
<img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot" />
<img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white" alt="Thymeleaf" />

### [Database]
<img src="https://img.shields.io/badge/postgresql-4169E1?style=for-the-badge&logo=postgresql&logoColor=white"/></a>
<img src="https://img.shields.io/badge/H2-004088?style=for-the-badge&logoColor=white"/></a>

### [AI]
<img src="https://img.shields.io/badge/Google%20Gemini-4285F4?style=for-the-badge&logo=googlegemini&logoColor=white" alt="Google Gemini" />

### [설계]
<img src="https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white" alt="Figma" /> <img src="https://img.shields.io/badge/ERDCloud-041E42?style=for-the-badge&logoColor=white" alt="ERDCloud" />

### [배포]
<img src="https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub" />

<br/>

# 3. 데이터베이스 구성도
![feelpick](https://github.com/user-attachments/assets/10b9b059-d619-4105-acff-0bc1e3577b35)

<br/>

# 4. 페이지 별 기능
### [홈]
![홈화면1](https://github.com/user-attachments/assets/e3a5ac6a-c193-4144-82a4-3d403563504d)
+ 로그인 전 홈 화면입니다.
+ 왼쪽 위 모서리에 있는 Feel Pick!을 누르면 홈으로 이동합니다.
+ 오른쪽 위 모서리에 있는 로그인과 회원가입을 누르면 각각 로그인과 회원가입 페이지로 이동합니다.
+ 기분 상태와 원하는 음식 종류를 선택하고, 추천 버튼을 누르면 메뉴 추천 페이지로 이동합니다.
+ 매일 그 날에 많이 먹은 음식의 순위를 3위까지 나열합니다.

<br/>

![홈화면2](https://github.com/user-attachments/assets/b6a49e20-017c-4da4-af5d-7430e0121bf8)
+ 로그인 후 홈 화면입니다.
+ 로그인 전 홈 화면과 다른 점은 오른쪽 위 모서리에 있는 로그아웃과 마이페이지를 누르면, 각각 로그아웃을 하거나 마이페이지로 이동합니다.

<br/>

### [회원가입]
![회원가입](https://github.com/user-attachments/assets/dc72b88e-35cf-451e-9a44-9e7b959688b0)
+ 사용자 이름과 비밀번호, 비밀번호 확인을 입력 받습니다. 이들은 모두 유효성 검증을 거칩니다.
+ 중복확인 버튼을 누르면 데이터베이스에서 같은 사용자 이름이 있는지 조회합니다.
+ 유효성 검증을 모두 통과하더라도 중복확인을 하지 않으면 회원가입되지 않습니다.
+ 맨 아래에는 이미 계정이 있는 경우, 로그인 페이지로 이동할 수 있도록 합니다.

<br/>

### [로그인]
![로그인](https://github.com/user-attachments/assets/b7589370-d904-49da-a7f7-8effbd8ee661)
+ 잘못된 회원정보를 입력하면, 오류 메세지를 표시합니다.
+ 올바른 회원정보를 입력하면, 세션에 로그인 정보를 저장하고 로그인 후 홈 화면으로 이동합니다.

<br/>

### [추천 메뉴]
![추천메뉴 - Clipchamp로 제작](https://github.com/user-attachments/assets/7c33ef29-1575-4761-aaa4-3baca00b95da)
+ 홈에서 감정 상태와 음식 종류를 선택 후, 추천해주세요 버튼을 눌렀을 때 나타나는 화면입니다.
+ 다시 추천 버튼을 누르면 다른 메뉴를 추천받을 수 있습니다.
+ 랜덤 선택 버튼을 누르면, 추천 메뉴 중에서 하나를 골라줍니다. 초기에는 고른 메뉴 페이지로 바로 넘어가도록 계획했으나, 사용자가 n번 눌러서 나오는 것 먹기 등과 같은 이벤트를 고려하여 선택만 하도록 했습니다.

<br/>

### [메뉴 정보]
![메뉴정보 - Clipchamp로 제작](https://github.com/user-attachments/assets/ae25a744-11d2-402f-83c4-3a122da109e6)
+ 선택한 메뉴에 대해서 레시피와 먹방 영상 링크를 제공합니다.
+ 레시피 정보를 요청하여 화면에 바로 보여주는 것도 생각했으나, 한 요리에 대한 레시피는 다양하다는 점과 응답받은 레시피의 형식이 통일되지 않는다는 점을 고려하여 만개의레시피로 연결되게 하였습니다.
+ 유튜브 영상 링크를 요청하여 embeded 형식으로 보여주려고 했으나, Gemini AI api가 특정 유튜브 영상을 제공할 수 없다는 점과 영상 선택의 다양성을 위해 유튜브로 연결되게 하였습니다.
+ 오늘 작성된 인증글을 최신순으로 볼 수 있습니다. 세션을 이용하여 글의 작성자에게만 수정과 삭제 버튼이 나타납니다. 수정 버튼을 누르면 게시글 수정 페이지로 이동하고, 삭제 버튼을 누르면 게시글이 삭제됩니다.
+ '나도 자랑할래요'를 누르면, 선택한 음식을 먹었다는 인증 글을 작성할 수 있는 페이지로 이동합니다.

<br/>

### [게시글 작성]
![게시글작성 - Clipchamp로 제작](https://github.com/user-attachments/assets/3789edbb-8204-4250-a0a5-170aee6ba513)
+ 가장 마음에 드는 음식 사진과 글로 게시글을 작성할 수 있습니다.

<br/>

### [게시글 수정]
![게시글수정 - Clipchamp로 제작](https://github.com/user-attachments/assets/26db0d23-6f02-4679-804b-5205ecc04fb5)
+ 게시글의 사진과 글을 변경할 수 있습니다.
+ 만약, 변경할 사진을 업로드하지 않으면, 기존 사진이 유지되도록 했습니다.

<br/>

### [마이페이지]
#### 비밀번호 변경
![비밀번호 변경 - Clipchamp로 제작](https://github.com/user-attachments/assets/a50cbae4-fe5a-4aa4-a30c-c10ccd02e06b)
+ 새로운 비밀번호는 유효성 검증을 거칩니다.
+ 새로운 비밀번호가 유효성 검증을 통과하더라도, 현재 비밀번호가 올바르지 않으면, 오류 메세지와 함께 돌아옵니다.

<br/>

#### 회원탈퇴
+ 탈퇴 의사를 한 번 더 확인 후, 회원 목록에서 삭제합니다.

<br/>

#### 내가 쓴 글
+ 로그인한 사용자가 작성한 글을 모아놓은 페이지로 이동합니다.

<br/>

# 5. 트러블 슈팅
+ <a href="https://hades1.tistory.com/145">어떤 게시물을 삭제해도 맨 앞의 게시물이 삭제되는 이슈</a>
+ <a href="https://hades1.tistory.com/148">음식 이름에 띄어쓰기가 있는 경우, 게시글이 기획한 대로 작성되지 않는 이슈</a>
+ <a href="https://hades1.tistory.com/149">동시성으로 인한 중복 가입 이슈</a>