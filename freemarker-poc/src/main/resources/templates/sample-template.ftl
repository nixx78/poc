<html>

<head>

</head>


<body>

<span>${totalAmount}</span>

<br>

<table>
    <thead>
        <th>ID</th>
        <th>Name</th>
        <th>Date of Birth</th>
    </thead>

    <tbody>
        <#foreach p in persons>
            <tr>
                <td>${p.id}</td>
                <td>${p.name} ${p.surname}</td>
                <td>${(p.dateOfBirth?string('dd/MM/yyy'))!}</td>
            </tr>
        </#foreach>
    </tbody>

</table>

</body>


</html>