<#import "captcha.ftlh" as captcha>

<#macro loginField>
    <div class="mb-2 row text-center">
        <label for="inputLogin" class="col-lg- col-form-label">Логин :</label>
        <div class="col-sm-3 mx-auto">
            <input type="text" name="username" value="<#if user??>${user.username}</#if>"
                   class="form-control ${((errorMap['usernameError'])??)?string('is-invalid', '')}" minlength="3" maxlength="15" id="inputLogin" placeholder="Логин" />
            <#if (errorMap['usernameError'])??>
                <div class="invalid-feedback">
                    ${(errorMap['usernameError'])!''}
                </div>
            </#if>
        </div>
    </div>
</#macro>

<#macro passwordField>
    <div class="mb-2 row text-center">
        <label for="inputPassword" class="col-lg- col-form-label">Пароль :</label>
        <div class="col-sm-3 mx-auto">
            <input type="password" name="password"
                   class="form-control ${((errorMap['passwordError'])??)?string('is-invalid', '')}" minlength="4" maxlength="15" id="inputPassword" placeholder="Пароль"
                   oninvalid="this.setCustomValidity('Длина пароля должна быть не меньше 4-х символов')" oninput="this.setCustomValidity('')" />
            <#if (errorMap['passwordError'])??>
                <div class="invalid-feedback">
                    ${(errorMap['passwordError'])!''}
                </div>
            </#if>
        </div>
    </div>
</#macro>

<#macro passwordConfirmField>
    <div class="mb-2 row text-center">
        <label for="inputPassword2" class="col-lg- col-form-label">Подтверждение пароля :</label>
        <div class="col-sm-3 mx-auto">
            <input type="password" name="password2"
                   class="form-control ${((errorMap['password2Error'])??)?string('is-invalid', '')}" minlength="4" maxlength="15" id="inputPassword2" placeholder="Confirm your password"
                   oninvalid="this.setCustomValidity('Длина пароля должна быть не меньше 4-х символов')" oninput="this.setCustomValidity('')" />
            <#if (errorMap['password2Error'])??>
                <div class="invalid-feedback">
                    ${(errorMap['password2Error'])!''}
                </div>
            </#if>
        </div>
    </div>
</#macro>

<#macro emailField>
    <div class="mb-2 row text-center">
        <label for="inputEmail" class="col-lg- col-form-label">Почта :</label>
        <div class="col-sm-3 mx-auto">
            <input type="email" name="email" value="<#if user??>${user.email}</#if>"
                   class="form-control ${((errorMap['emailError'])??)?string('is-invalid', '')}" id="inputEmail" placeholder="example@gmail.com"
                   oninvalid="this.setCustomValidity('Некорректный формат почты')" oninput="this.setCustomValidity('')" required />
            <#if (errorMap['emailError'])??>
                <div class="invalid-feedback">
                    ${(errorMap['emailError'])!''}
                </div>
            </#if>
        </div>
    </div>
</#macro>

<#macro recaptchaField>
    <div class="mb-2 row">
        <div class="col-sm-3 mx-auto mt-2">
            <@captcha.captcha "${recaptchaSiteKey}"/>

            <#if (errorMap['recaptchaError'])??>
                <div class="alert alert-danger mt-2" role="alert">
                    ${(errorMap['recaptchaError'])!''}
                </div>
            </#if>
        </div>
    </div>
</#macro>