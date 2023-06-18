<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />

        <title>Lazy</title>
        <link rel="stylesheet" href="/static/css/style.css" />

        <!-- Include Bootstrap CSS file -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous" />
        <!-- Include custom CSS file after Bootstrap CSS file -->
        <link rel="stylesheet" href="/static/css/theme.css" />
        <link rel="stylesheet" href="/static/css/chat.css" />
        <link rel="stylesheet" href="/static/css/news.css" />

        <!-- Google Font -->
        <link rel="preconnect" href="https://fonts.googleapis.com"></link>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&amp;display=swap" />

        <script src="https://www.google.com/recaptcha/api.js" async="async" defer="defer"></script>
    </head>

    <body data-bs-theme="dark" class="custom-bg-color">
        <#include "navbar.ftlh">
        <div class="container">
            <div class="mt-3">
                <#nested>
            </div>
        </div>
        <!-- Include Bootstrap JavaScript file -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
    </body>
    </html>
</#macro>
