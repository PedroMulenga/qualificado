const sign_up = document.getElementById('sign-up'),
    sign_in = document.getElementById('sign-in'),
    login_in = document.getElementById('login-in'),
    login_up = document.getElementById('login-up')

sign_up.addEventListener('click', () => {
    login_in.classList.remove('block')
    login_up.classList.remove('none')

    login_in.classList.add('none')
    login_up.classList.add('block')
})

sign_in.addEventListener('click', () => {
    login_in.classList.remove('none')
    login_up.classList.remove('block')

    login_in.classList.add('block')
    login_up.classList.add('none')
});