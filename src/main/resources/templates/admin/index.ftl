<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, ftpUser-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../css/global.css">
    <title>Hydrogen FTP Server Management</title>
</head>
<body>
<form action="login" method="post">
    <div class="titled-pane">
        <div class="title">Login</div>
        <div class="body">
            <div class="form-field">
                <span class="field-label">Username:</span>
                <input type="text" name="username" id="username">
            </div>
            <div class="form-field">
                <span class="field-label">Password:</span>
                <input type="password" name="password" id="password">
            </div>
        </div>
        <#if message??>
            <div class="message">
                ${message}
            </div>
        </#if>
        <div class="footer">
            <button type="submit">Login</button>
        </div>
    </div>
</form>
</body>
</html>