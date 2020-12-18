<html>

<head>

</head>


<body>

Total amount: <span>${totalAmount!"None"}</span>
<br>
Tax: <span>${tax!"None"}</span>     <!-- Check for null with default value -->
<p>Customer types: ${type?join(", ", "None")}</p>

<br>

<table>
    <thead>
        <th>ID</th>
        <th>Name</th>
        <th>Date of Birth</th>
    </thead>

    <tbody>
        <#foreach c in customers>
            <tr>
                <td>${c.id}</td>
                <td>${c.name} ${c.surname}</td>
                <td>${(c.dateOfBirth?string('dd/MM/yyy'))!}</td>
            </tr>
        </#foreach>
    </tbody>
</table>

<br>
<#include "/email-footer.html">

</body>

</html>