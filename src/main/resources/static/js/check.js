const main = () => {
    const formCadastro = document.getElementById('FormCadastro');
    const email = document.getElementById('Clogin');
    const senha = document.getElementById('Cpassword');

    const eyeOpen = document.querySelectorAll('.fa-solid.fa-eye');
    const eyeClose = document.querySelectorAll('.fa-solid.fa-eye-slash');

    eyeOpen.forEach((eye) => {
        eye.addEventListener('click', () => {
            eye.previousElementSibling.previousElementSibling.type = 'text';
            eye.classList.add('close');
            eye.nextElementSibling.classList.remove('close');
        });
    });

    eyeClose.forEach((eye) => {
        eye.addEventListener('click', () => {
            eye.previousElementSibling.previousElementSibling.previousElementSibling.type = 'password';
            eye.classList.add('close');
            eye.previousElementSibling.classList.remove('close');
        });
    });

    const handleValidation = () => {
        validatePassword(senha.value);
    };

    senha.addEventListener('keydown', handleValidation);
    senha.addEventListener('input', handleValidation);
    senha.addEventListener('change', handleValidation);

    email.addEventListener('input', () => validateEmail(email));
    
    formCadastro.addEventListener('submit', (e) => {
        e.preventDefault();

        const emailValido = validateEmail(email);
        const senhaValida = validatePassword(senha.value);
        const senhasIguais = comparePassword();

        if (emailValido && senhaValida && senhasIguais) {
            formCadastro.submit();
        }
    });
}

const validateEmail = (email) => {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    const emailValido = emailRegex.test(email.value);

    if (!emailValido) {
        email.setCustomValidity("Email inválido");
    } else {
        email.setCustomValidity("");
    }

    email.reportValidity();
    return emailValido;
}

const validatePassword = (senha) => {
    const minLength = senha.length >= 8;
    const hasUppercase = /[A-Z]/.test(senha);
    const hasNumber = /\d/.test(senha);
    const hasSpecialChar = /[!@#$%^&*()_\-+={}[\]|:;"'<>,.?/~`]/.test(senha);

    document.getElementById("minLength").style.color = minLength ? "green" : "red";
    document.getElementById("hasUppercase").style.color = hasUppercase ? "green" : "red";
    document.getElementById("hasNumber").style.color = hasNumber ? "green" : "red";
    document.getElementById("hasSpecialChar").style.color = hasSpecialChar ? "green" : "red";

    return minLength && hasUppercase && hasNumber && hasSpecialChar;
}

const comparePassword = () => {
    const password = document.getElementById('Cpassword');
    const confirmPassword = document.getElementById('ConfirmCPassword');

    if (password.value !== confirmPassword.value) {
        confirmPassword.setCustomValidity('Senhas divergentes');
        return false;
    } else {
        confirmPassword.setCustomValidity('');
        return true;
    }
}

window.onload = main;