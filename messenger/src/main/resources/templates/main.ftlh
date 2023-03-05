<#import "parts/common.ftlh" as c>

<@c.page>
    <form method="get" action="/main" class="row g-2">
        <div class="col-md-4">
            <input type="text" class="form-control" name="filter" value="${filter!}" placeholder="Search">
        </div>

        <div class="col-md-2">
            <button type="submit" class="btn btn-outline-primary">Найти</button>
        </div>
    </form>

    <a class="btn btn-primary my-2" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new Message
    </a>
    <div class="collapse show" id="collapseExample">
        <form method="post" enctype="multipart/form-data" class="col-md-4 needs-validation">
            <div class="col mb-1">
                <input type="text" name="text" class="form-control ${((errorMap['textError'])??)?string('is-invalid', '')}" id="validationCustom01" value="<#if message??>${message.text}</#if>" placeholder="Введите сообщение">
                <#if (errorMap['textError'])??>
                    <div class="invalid-feedback">
                        ${(errorMap['textError'])!''}
                    </div>
                </#if>
            </div>

            <div class="col mb-1">
                <input type="text" name="tag" class="form-control ${((errorMap['tagError'])??)?string('is-invalid', '')}" value="<#if message??>${message.tag}</#if>" placeholder="Тэг">
                <#if (errorMap['tagError'])??>
                    <div class="invalid-feedback">
                        ${(errorMap['tagError'])!''}
                    </div>
                </#if>
            </div>

            <div class="col mb-2">
                <input type="file" name="file" class="form-control" aria-label="file example">
                <div class="invalid-feedback">Example invalid form file feedback</div>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div class="mb-2">
                <button class="btn btn-primary" type="submit">Submit form</button>
            </div>
        </form>
    </div>

    <div class="row row-cols-1 row-cols-md-3 g-4">
        <#list messages as message>
            <div class="col">
                <div class="card">
                    <#if message.filename??>
                        <img src="/img/${message.filename}" class="card-img-top">
                    </#if>
                    <div class="m-2">
                        <span>${message.text}</span><br>
                        <i>#${message.tag}</i>
                    </div>
                    <div class="card-footer text-muted">
                        ${message.authorName}
                    </div>
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
</@c.page>