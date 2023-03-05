<#macro auth path isRegisterForm>
    <form action="${path}" method="post">
        <div class="mb-2 row">
            <label for="inputLogin" class="col-lg- col-form-label">Login :</label>
            <div class="col-sm-5">
                <input type="text" name="username" value="<#if user??>${user.username}</#if>"
                       class="form-control ${((errorMap['usernameError'])??)?string('is-invalid', '')}" id="inputLogin" placeholder="User name">
                <#if (errorMap['usernameError'])??>
                    <div class="invalid-feedback">
                        ${(errorMap['usernameError'])!''}
                    </div>
                </#if>
            </div>
        </div>

        <div class="mb-2 row">
            <label for="inputPassword" class="col-lg- col-form-label">Password :</label>
            <div class="col-sm-5">
                <input type="password" name="password"
                       class="form-control ${((errorMap['passwordError'])??)?string('is-invalid', '')}" id="inputPassword" placeholder="Password">
                <#if (errorMap['passwordError'])??>
                    <div class="invalid-feedback">
                        ${(errorMap['passwordError'])!''}
                    </div>
                </#if>
            </div>
        </div>

        <#if isRegisterForm>
            <div class="mb-2 row">
                <label for="inputPassword2" class="col-lg- col-form-label">Password :</label>
                <div class="col-sm-5">
                    <input type="password" name="password2"
                           class="form-control ${(passwordError??)?string('is-invalid', '')}" id="inputPassword2" placeholder="Password">
                    <#if passwordError??>
                        <div class="invalid-feedback">
                            ${passwordError!''}
                        </div>
                    </#if>
                </div>
            </div>

            <div class="mb-2 row">
                <label for="inputEmail" class="col-lg- col-form-label">Email :</label>
                <div class="col-sm-5">
                    <input type="email" name="email" value="<#if user??>${user.email}</#if>"
                           class="form-control ${((errorMap['emailError'])??)?string('is-invalid', '')}" id="inputEmail" placeholder="example@gmail.com">
                    <#if (errorMap['emailError'])??>
                        <div class="invalid-feedback">
                            ${(errorMap['emailError'])!''}
                        </div>
                    </#if>
                </div>
            </div>
        </#if>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <#if !isRegisterForm>
            <a href="/registration">Add new user</a>
        </#if>

        <div class="col-auto mt-3">
            <button type="submit" class="btn btn-primary">${isRegisterForm?then("Create", "Sign In")}</button>
        </div>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit" class="btn btn-outline-primary">Sign Out</button>
    </form>
</#macro>