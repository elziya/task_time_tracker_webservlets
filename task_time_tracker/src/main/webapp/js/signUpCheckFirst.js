document.addEventListener('DOMContentLoaded', function() {
    const EMAIL_REGEX = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

    let btn = document.getElementsByTagName('button')[0];
    btn.setAttribute('disabled', true);

    let isValidPass = function (){
        let passHelp = document.getElementById('passHelp');

        let pass = document.getElementById('password');
        let pass2 = document.getElementById('password2');

        if (pass.value.length < 8) {
            passHelp.innerText = 'Your password must contains at least 8 characters';
            return false;
        }
        if (pass.value !== pass2.value) {
            passHelp.innerText = 'The passwords you entered do not match';
            return false;
        }
        passHelp.innerText = null;
        return true;
    };

    let isValidEmail = function (){

        let email = document.getElementById('email');

        let emailHelp = document.getElementById('emailHelp');
        if(email.value.match(EMAIL_REGEX) === null) {
            emailHelp.innerText = 'Your email is incorrect';
            return false;
        }
        emailHelp.innerText = null;
        return true;
    }

    let isValid = function (){
        if(isValidEmail() && isValidPass()){
            btn.removeAttribute('disabled');

        } else {
            btn.setAttribute('disabled', true);

        }
    }

    let elements = ['email', 'firstName', 'lastName', 'password', 'password2'];
    elements.forEach(elem => {
        let param = document.getElementById(elem);
        param.onchange = isValid;

    })
});