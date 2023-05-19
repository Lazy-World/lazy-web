<#macro updForm isLogEdit>
    <div class="text-center">
        <a class="btn btn-light my-2" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
            <#if isLogEdit>
                Редактировать новость
            <#else>
                Добавить новость
            </#if>
        </a>
    </div>

    <div class="collapse" id="collapseExample">
        <form method="post" enctype="multipart/form-data" class="col-md-3 mx-auto needs-validation">
            <div class="col mb-1">
                <input type="text" name="title" class="form-control ${((errorMap['titleError'])??)?string('is-invalid', '')}" value="<#if currentLog??>${currentLog.title}</#if>" placeholder="Заголовок" />
                <#if (errorMap['titleError'])??>
                    <div class="invalid-feedback">
                        ${(errorMap['titleError'])!''}
                    </div>
                </#if>
            </div>

            <div class="col mb-1">
                <textarea type="text" name="text" class="form-control ${((errorMap['textError'])??)?string('is-invalid', '')}" placeholder="текст"><#if currentLog??>${currentLog.text}</#if></textarea>
                <#if (errorMap['textError'])??>
                    <div class="invalid-feedback">
                        ${(errorMap['textError'])!''}
                    </div>
                </#if>
            </div>

            <div class="col mb-2">
                <input type="file" name="file" class="form-control ${(fileError??)?string('is-invalid', '')}" aria-label="file example" />
                <#if fileError??>
                    <div class="invalid-feedback">${fileError}</div>
                </#if>
            </div>

            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div class="mb-2">
                <button class="btn btn-success" type="submit">
                    <#if isLogEdit>
                        Сохранить
                    <#else>
                        Опубликовать
                    </#if>
                </button>
            </div>
        </form>
    </div>
</#macro>