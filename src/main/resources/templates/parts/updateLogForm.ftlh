<#macro updForm isLogEdit>
    <form method="post" enctype="multipart/form-data" class="col-md-3 mx-auto needs-validation">
        <div class="col mb-1">
            <input type="text" name="title" class="VkIdForm__input ${((errorMap['titleError'])??)?string('is-invalid', '')}" value="<#if currentLog??>${currentLog.title}</#if>" placeholder="Заголовок..." />
            <#if (errorMap['titleError'])??>
                <div class="invalid-feedback">
                    ${(errorMap['titleError'])!''}
                </div>
            </#if>
        </div>

        <div class="col mb-1">
            <textarea id="my-textarea" type="text" name="text" class="VkIdForm__input ${((errorMap['textError'])??)?string('is-invalid', '')}" placeholder="Текст новости..."><#if currentLog??>${currentLog.text}</#if></textarea>
            <#if (errorMap['textError'])??>
                <div class="invalid-feedback">
                    ${(errorMap['textError'])!''}
                </div>
            </#if>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <div class="mb-2 d-grid gap-2 col-6 mx-auto">
            <button class="btn btn-success" type="submit">
                <#if isLogEdit>
                    Сохранить
                <#else>
                    Опубликовать
                </#if>
            </button>
        </div>
    </form>
</#macro>