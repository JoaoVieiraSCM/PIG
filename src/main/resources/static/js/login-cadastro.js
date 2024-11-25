const container = document.querySelector('.container');
const registerBtn = document.querySelector('.register-btn');
const loginBtn = document.querySelector('.login-btn');
const inputs = document.querySelectorAll('input');

registerBtn.addEventListener('click', () => {
  container.classList.add('active');
  cleanFields();
})

loginBtn.addEventListener('click', () => {
    container.classList.remove('active');
    cleanFields();
})

cleanFields = () => {
  inputs.forEach(input => {
    input.value = '';
  });
  document.getElementById("minLength").style.color = 'black';
  document.getElementById("hasUppercase").style.color = 'black';
  document.getElementById("hasNumber").style.color = 'black';
  document.getElementById("hasSpecialChar").style.color = 'black';
}