
document.getElementById('userEmail').addEventListener("focusout", () => {

    const btn = document.getElementById('signup-submit-button');

    if(document.getElementById('userEmail').value && document.getElementById('userPwd').value) {

        btn.style.backgroundColor = '#40E15A';
    } else {

        btn.style.backgroundColor = '#717171';
    }
});

document.getElementById('userPwd').addEventListener("focusout", () => {

    const btn = document.getElementById('signup-submit-button');

    if(document.getElementById('userEmail').value && document.getElementById('userPwd').value) {

        btn.style.backgroundColor = '#40E15A';
    } else {

        btn.style.backgroundColor = '#717171';
    }
});

document.getElementById('naver-signup').addEventListener("click", () => {

    window.location.href = "/oauth2/authorization/naver";
});

document.getElementById('kakao-signup').addEventListener("click", () => {

    window.location.href = "/oauth2/authorization/kakao";
});

function signUpUser() {

    const form = document.querySelector('form');

    const email = document.getElementById('userEmail').value;
    const emailPattern = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;

    const pwd = document.getElementById('userPwd').value;
    const pwdPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+}{"':;?/>.<,])(?!.*\s).{8,20}$/;

    if(emailPattern.test(email)) {

        if(pwdPattern.test(pwd)) {

            form.submit();
        }

        alert("비밀번호를 규칙에 맞게 작성해주세요.")
    }

    alert("이메일을 확인해주세요.");
}
