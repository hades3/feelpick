let usernameInput = document.querySelector('.username');
let usernameMessage = document.querySelector('.username-message');

let passwordInput = document.querySelector('.password');
let passwordMessage = document.querySelector('.password-message');

let confirmInput = document.querySelector('.confirm-password');
let confirmMessage = document.querySelector('.confirm-message');

function validateUsername(){

    let username = usernameInput.value;

    if (usernameMessage.classList.contains('success')){
        usernameMessage.classList.remove('success');
    }

    if (validateUsernameLength(username) && validateUsernameFormat(username)){
        usernameMessage.style.color = "mediumseagreen";
        usernameMessage.textContent = "멋진 이름이네요!";
        return true;
    }
    else{
        usernameMessage.style.color = "red";
        usernameMessage.textContent = "사용자 이름은 영어 대소문자 또는 숫자를 사용하여 4글자 이상 10글자 이하이어야 해요.";
        return false;
    }
}

function validateUsernameLength(username){
    return username.length >= 4 && username.length <= 10;
}

function validateUsernameFormat(username){
    return /^[0-9a-zA-Z]*$/.test(username);
}

function validateDuplicateUsername(){
    let username = usernameInput.value;

    if (usernameMessage.textContent !== "멋진 이름이네요!"){
        alert("사용자 이름을 먼저 올바르게 지어주세요!");
        usernameInput.focus();
        return;
    }

    let xhr = new XMLHttpRequest();
    xhr.open('GET', `/member/validate?username=${encodeURIComponent(username)}`, true);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                let response = JSON.parse(xhr.responseText);
                if (!response.isAvailable) {
                    alert("아쉽지만 이미 존재하는 이름이에요.");
                }
                else{
                    alert("사용 가능한 사용자 이름이에요!");
                    usernameMessage.classList.add('success');
                }
            }
        }
    }

    xhr.send();
}

function validatePassword(){

    let password = passwordInput.value;

    if (validatePasswordLength(password) && validatePasswordFormat(password)){
        passwordMessage.textContent = "";
        passwordMessage.classList.add('hide');
        return true;
    }
    else{
        passwordMessage.classList.remove('hide');
        passwordMessage.textContent = "비밀번호는 영어 대소문자, 숫자, 특수문자(!,@,$,%,^,&,?)를 조합해서 8글자 이상 20글자 이하이어야 해요."
        return false;
    }
}

function validatePasswordLength(password){
    return password.length >= 8 && password.length <= 20;
}

function validatePasswordFormat(password){
    return /(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@$%^&?])[A-Za-z0-9!@$%^&?]*$/.test(password);
}

function validateConfirm(){

    let password = passwordInput.value;
    let confirm = confirmInput.value;

    if (password !== confirm){
        confirmMessage.classList.remove('hide');
        confirmMessage.textContent = "비밀번호가 일치하지 않아요.";
        return false;
    }
    else{
        confirmMessage.textContent = "";
        confirmMessage.classList.add('hide');
        return true;
    }
}

function validateAccount(){
    if (!validatePassword()){
        passwordInput.focus();
        return false;
    }
    if (!validateConfirm()){
        confirmInput.focus();
        return false;
    }
    if (!usernameMessage.classList.contains('success')){
        alert("사용자 이름 중복 검사가 필요해요.");
        usernameInput.focus();
        return false;
    }

    return true;
}

/* mypage */
function changePassword(){
    if (confirm("비밀번호를 변경하시겠어요?")){
        return validatePassword() && validateConfirm();
    }
    else{
        return false;
    }
}


// JavaScript를 이용한 탭 기능 구현
function showTab(tabId) {
    var contents = document.querySelectorAll('.tab-content');
    var tabs = document.querySelectorAll('.tabs');

    contents.forEach(content => content.classList.remove('active'));
    tabs.forEach(tab => tab.classList.remove('active'));

    document.getElementById(tabId).classList.add('active');
    document.querySelector(`.tabs[onclick="showTab('${tabId}')"]`).classList.add('active');
}

// 기본적으로 첫 번째 탭을 활성화
showTab('changePasswordTab')