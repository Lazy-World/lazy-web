<#import "parts/common.ftlh" as c>

<@c.page>
    <div class="mb-2">
        <h4>Welcome, ${username}!</h4>
    </div>

    <form method="post">
        <div class="mb-2 row">
            <label for="inputEmail" class="col-lg- col-form-label">Email :</label>
            <div class="col-sm-5">
                <input type="email" name="email" class="form-control" id="inputEmail" placeholder="example@gmail.com" value="${email!''}" />
            </div>
        </div>

        <div class="mb-2 row">
            <label for="inputPassword" class="col-lg- col-form-label">Password :</label>
            <div class="col-sm-5">
                <input type="password" name="password" class="form-control" id="inputPassword" placeholder="Password" />
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />

        <div class="col-auto mt-3">
            <button type="submit" class="btn btn-primary">Save</button>
        </div>
    </form>
</@c.page>