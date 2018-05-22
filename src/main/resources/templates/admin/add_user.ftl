<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../css/global.css">
    <title>Hydrogen FTP Server Management</title>
</head>
<body>
<form action="add_user" method="post">
    <div class="titled-pane">
        <div class="title">Add User</div>
        <div class="body">
            <div class="form-field">
                <span class="field-label">User Name:</span>
                <input type="text" name="username" id="username">
            </div>
            <div class="form-field">
                <span class="field-label">Display Name:</span>
                <input type="text" name="displayName" id="displayName">
            </div>
            <div class="form-field">
                <span class="field-label">Password:</span>
                <input type="password" name="password" id="password">
            </div>
        </div>
        <div class="footer">
            <button type="submit">Save</button>
        </div>
    </div>
</form>
</body>
</html>