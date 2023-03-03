<#macro auth path isRegisterForm>
    <form action="${path}" method="post">
        <div class="mb-2 row">
            <label for="inputLogin" class="col-lg- col-form-label">Login :</label>
            <div class="col-sm-5">
                <input type="text" name="username" class="form-control" id="inputLogin" placeholder="User name">
            </div>
        </div>

        <div class="mb-2 row">
            <label for="inputPassword" class="col-lg- col-form-label">Password :</label>
            <div class="col-sm-5">
                <input type="password" name="password" class="form-control" id="inputPassword" placeholder="Password">
            </div>
        </div>

        <#if isRegisterForm>
            <div class="mb-2 row">
                <label for="inputEmail" class="col-lg- col-form-label">Email :</label>
                <div class="col-sm-5">
                    <input type="email" name="email" class="form-control" id="inputEmail" placeholder="example@gmail.com">
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