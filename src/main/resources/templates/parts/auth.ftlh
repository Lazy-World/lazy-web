<#import "captcha.ftlh" as captcha>

<#macro loginField>
    <div class="row mb-3">
<#--        <label for="inputEmail" class="col-lg- mb-1">Логин</label>-->
        <div class="mx-auto">
            <input type="text" name="username" value="<#if user??>${user.username}</#if>"
                   class="VkIdForm__input ${((errorMap['usernameError'])??)?string('is-invalid', '')}" minlength="3" maxlength="15" id="inputLogin" placeholder="Логин" />
            <#if (errorMap['usernameError'])??>
                <div class="invalid-feedback">
                    ${(errorMap['usernameError'])!''}
                </div>
            </#if>
        </div>
    </div>
</#macro>

<#macro passwordField>
    <div class="row mb-3">
<#--        <label for="inputEmail" class="col-lg- mb-1">Пароль</label>-->
        <div class="mx-auto">
            <input type="password" name="password"
                   class="VkIdForm__input ${((errorMap['passwordError'])??)?string('is-invalid', '')}" minlength="4" maxlength="15" id="inputPassword" placeholder="Пароль"
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
    <div class="row mb-3">
<#--        <label for="inputEmail" class="col-lg- mb-1">Подтверждение пароля</label>-->
        <div class="mx-auto">
            <input type="password" name="password2"
                   class="VkIdForm__input ${((errorMap['password2Error'])??)?string('is-invalid', '')}" minlength="4" maxlength="15" id="inputPassword2" placeholder="Подтверждение пароля"
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
    <div class="row mb-3">
<#--        <label for="inputEmail" class="col-lg- mb-1">Почта</label>-->
        <div class="mx-auto">
            <input type="email" name="email" value="<#if user??>${user.email}</#if>"
                   class="VkIdForm__input ${((errorMap['emailError'])??)?string('is-invalid', '')}" id="inputEmail" placeholder="example@gmail.com"
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
        <div class="mx-auto mt-3 mb-3">
            <@captcha.captcha "${recaptchaSiteKey}"/>

            <#if (errorMap['recaptchaError'])??>
                <div class="alert alert-danger mt-2" role="alert">
                    ${(errorMap['recaptchaError'])!''}
                </div>
            </#if>
        </div>
</#macro>