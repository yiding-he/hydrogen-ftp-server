<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, ftpUser-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../css/global.css">
    <title>Hydrogen FTP Server Management</title>
    <script>
        function delete_user(userid) {
            if (!confirm('Are you sure?')) {
                return;
            }

            document.getElementById('deleteUserId').value = userid;
            document.getElementById('deleteUserForm').submit();
        }

        function delete_group(groupId) {
            if (!confirm('Are you sure?')) {
                return;
            }

            document.getElementById('deleteGroupId').value = groupId;
            document.getElementById('deleteGroupForm').submit();
        }
    </script>
</head>
<body>
<form action="delete_user" method="post" style="display: none;" id="deleteUserForm">
    <input type="hidden" name="userId" id="deleteUserId">
</form>
<form action="delete_group" method="post" style="display: none;" id="deleteGroupForm">
    <input type="hidden" name="groupId" id="deleteGroupId">
</form>
<div class="titled-pane">
    <div class="title">Users</div>
    <div class="body">
        <table class="datatable">
            <thead>
            <tr>
                <td>User Name</td>
                <td>Display Name</td>
                <td>Groups</td>
                <td></td>
            </tr>
            </thead>
            <tbody>
            <#if users??>
                <#list users as user>
                <tr>
                    <td>${user.username}</td>
                    <td>${user.displayName}</td>
                    <td>${user.groupNames!}</td>
                    <td>
                        <a href="edit_user?userId=${user.id?long?c}"><button>Edit</button></a>
                        <button type="button" onclick="delete_user('${user.id?long?c}')">Delete</button>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
    <div class="footer">
        <a href="add_user">
            <button>Add User</button>
        </a>
    </div>
</div>
<br/>
<div class="titled-pane">
    <div class="title">User Groups</div>
    <div class="body">
        <table class="datatable">
            <thead>
            <tr>
                <td>Group Name</td>
                <td>User Count</td>
                <td> </td>
            </tr>
            </thead>
            <tbody>
            <#if groups??>
                <#list groups as group>
                    <tr>
                        <td>${group.groupName}</td>
                        <td>${group.userCount}</td>
                        <td>
                            <a href="edit_group?groupId=${group.id?long?c}"><button>Edit</button></a>
                            <button type="button" onclick="delete_group('${group.id?long?c}')">Delete</button>
                        </td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
    <div class="footer">
        <a href="add_group">
            <button>Add User Group</button>
        </a>
    </div>
</div>
<br/>
<div class="titled-pane">
    <div class="title">Server</div>
    <div class="body">
        <#if server_running == true>
            Server is running.
        <#else>
            Server is stopped.
        </#if>

    </div>
    <div class="footer">
        <form action="server_action" method="post" onsubmit="return confirm('Are you sure?')">
            <#if server_running == true>
                <button type="submit" name="action" value="stop">Stop Server</button>
            <#else>
                <button type="submit" name="action" value="start">Start Server</button>
            </#if>
        </form>
    </div>
</div>
</body>
</html>