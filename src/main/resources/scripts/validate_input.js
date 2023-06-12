function isLoginCorrect(login) {
    const regex = new RegExp("^[A-Za-z0-9_-]{3,10}$");
    return regex.test(login);
}

