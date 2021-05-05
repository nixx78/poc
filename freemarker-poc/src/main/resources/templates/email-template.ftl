<html>

<head>
</head>

<#macro tableContent data>
<table>
    <thead>
        <th>ID</th>
        <th>Name</th>
        <th>Date of Birth</th>
    </thead>
     <tbody>
        <#foreach c in data>
            <tr>
                <td>${c.id}</td>
                <td>${c.name} ${c.surname}</td>
                <td>${(c.dateOfBirth?string('dd/MM/yyy'))!}</td>
            </tr>
        </#foreach>
     </tbody>
</table>
</#macro>

<body>

Total amount: <span>${totalAmount!"None"}</span>
<br>
Tax: <span>${tax!"None"}</span>     <!-- Check for null with default value -->
<p>Customer types: ${type?join(", ", "None")}</p>
<p>Calculated total: ${calculateTotal()}</p>

<p>Converted value '0': ${convert(0)}
<br>
Converted value '1': ${convert(1)}
</p>

<#if calculateTotal()!=0 >
    <p>Total greater than zero</p>
</#if>


<br>
<p>All Customers</p>
<@tableContent data=customers/>

<p>Vip Customers</p>
<@tableContent data=getVipCustomers()/>

<p>All customers using list-item</p>
<#list customers>
  <ul>
    <#items as c>
      <li>#${c.id}  ${c.name} ${c.surname}</li>
    </#items>
  </ul>
<#else>
   No Data
</#list>

<br>
<#include "/email-footer.html">

</body>



</html>