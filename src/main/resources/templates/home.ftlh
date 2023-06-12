<#import "parts/common.ftlh" as c>
<#import "parts/security.ftlh" as s>
<#import "parts/updateLogForm.ftlh" as updLog>

<@c.page>
<div id="carouselExampleIndicators" class="carousel slide">
    <div class="carousel-indicators">
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
    </div>
    <div class="carousel-inner mb-3">
        <div class="carousel-item active">
            <img src="../static/preview_1.png" class="d-block w-100 carousel-image" alt="картинка_1"></img>
        </div>
        <div class="carousel-item">
            <img src="../static/preview_2.png" class="d-block w-100 carousel-image" alt="картинка_2"></img>
        </div>
        <div class="carousel-item">
            <img src="../static/preview_3.png" class="d-block w-100 carousel-image" alt="картинка_3"></img>
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>

<#if s.isAdmin>
    <@updLog.updForm false/>
</#if>

<div class="row row-cols-1 row-cols-md-3" id="update-log-list">
    <#list updateLogs as updateLog>
        <div class="col">
            <div class="card mx-auto mb-4" style="width: 18rem;">
                <img src="/img/${updateLog.getFilename()}." class="card-img-top" alt="Картинка">
                <div class="card-body">
                    <h5 class="card-title">${updateLog.getTitle()}</h5>
                    <p class="card-text">${updateLog.getText()}</p>
                    <#if s.isAdmin>
                        <a href="/edit?log=${updateLog.getId()}" class="btn btn-primary">Редактировать</a>
                    </#if>
                </div>
            </div>
        </div>

<#--        <div class="col" data-id="${updateLog.getId()}">-->
<#--            <div class="card mx-auto mb-4" style="max-width: 540px;">-->
<#--                <div class="row g-0">-->
<#--                    <div class="col-md-4">-->
<#--                        <img src="/img/${updateLog.getFilename()}" class="img-fluid rounded-start" alt="картинка" />-->
<#--                    </div>-->
<#--                    <div class="col-md-8">-->
<#--                        <div class="card-body">-->
<#--                            <h5 class="card-title">${updateLog.getTitle()}</h5>-->
<#--                            <p class="card-text">${updateLog.getText()}</p>-->
<#--                        </div>-->
<#--                    </div>-->
<#--                    <div class="card-footer">-->
<#--                        <small class="text-muted">Автор: <b>${updateLog.authorName}</b></small>-->
<#--                        <#if s.isAdmin>-->
<#--                            <a class="btn btn-outline-secondary" href="/edit?log=${updateLog.getId()}">Редактировать</a>-->
<#--                        </#if>-->
<#--                    </div>-->
<#--                </div>-->
<#--            </div>-->
<#--        </div>-->
    <#else>
        <div class="text-center mt-3">Новостей пока нет</div>
    </#list>
</div>
</@c.page>