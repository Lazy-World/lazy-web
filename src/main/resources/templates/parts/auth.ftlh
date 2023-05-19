<#import "captcha.ftlh" as c>

<#macro auth path isRegisterForm>
    <form action="${path}" method="post">
        <div class="mb-2 row text-center">
            <label for="inputLogin" class="col-lg- col-form-label">Логин :</label>
            <div class="col-sm-3 mx-auto">
                <input type="text" name="username" value="<#if user??>${user.username}</#if>"
                       class="form-control ${((errorMap['usernameError'])??)?string('is-invalid', '')}" id="inputLogin" placeholder="Логин" />
                <#if (errorMap['usernameError'])??>
                    <div class="invalid-feedback">
                        ${(errorMap['usernameError'])!''}
                    </div>
                </#if>
            </div>
        </div>

        <div class="mb-2 row text-center">
            <label for="inputPassword" class="col-lg- col-form-label">Пароль :</label>
            <div class="col-sm-3 mx-auto">
                <input type="password" name="password"
                       class="form-control ${((errorMap['passwordError'])??)?string('is-invalid', '')}" id="inputPassword" placeholder="Пароль" />
                <#if (errorMap['passwordError'])??>
                    <div class="invalid-feedback">
                        ${(errorMap['passwordError'])!''}
                    </div>
                </#if>
            </div>
        </div>

        <#if isRegisterForm>
            <div class="mb-2 row text-center">
                <label for="inputPassword2" class="col-lg- col-form-label">Подтверждение пароля :</label>
                <div class="col-sm-3 mx-auto">
                    <input type="password" name="password2"
                           class="form-control ${((errorMap['password2Error'])??)?string('is-invalid', '')}" id="inputPassword2" placeholder="Confirm your password" />
                    <#if (errorMap['password2Error'])??>
                        <div class="invalid-feedback">
                            ${(errorMap['password2Error'])!''}
                        </div>
                    </#if>
                </div>
            </div>

            <div class="mb-2 row text-center">
                <label for="inputEmail" class="col-lg- col-form-label">Почта :</label>
                <div class="col-sm-3 mx-auto">
                    <input type="email" name="email" value="<#if user??>${user.email}</#if>"
                           class="form-control ${((errorMap['emailError'])??)?string('is-invalid', '')}" id="inputEmail" placeholder="example@gmail.com" />
                    <#if (errorMap['emailError'])??>
                        <div class="invalid-feedback">
                            ${(errorMap['emailError'])!''}
                        </div>
                    </#if>
                </div>
            </div>

            <div class="mb-2 row">
                <div class="col-sm-3 mx-auto mt-2">
                    <@c.captcha "${recaptchaSiteKey}"/>

                    <#if (errorMap['recaptchaError'])??>
                        <div class="alert alert-danger mt-2" role="alert">
                            ${(errorMap['recaptchaError'])!''}
                        </div>
                    </#if>
                </div>
            </div>
        </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isRegisterForm>
            <a href="/registration">Регистрация</a>
        </#if>

        <div class="col-auto mt-2 text-center">
            <button type="submit" class="btn btn-primary">${isRegisterForm?then("Создать", "Войти")}</button>
        </div>
    </form>
</#macro>