<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register Customer</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        h1 {
            color: #333;
        }

        form {
            max-width: 600px;
            margin-top: 20px;
        }

        .account-section {
            display: none;
            border: 1px solid #ccc;
            padding: 10px;
            margin-top: 10px;
            background-color: #f9f9f9;
        }

        .form-group {
            margin-bottom: 10px;
        }

        label {
            display: inline-block;
            min-width: 150px;
        }

        input[type="text"],
        input[type="email"],
        input[type="date"],
        input[type="number"] {
            padding: 5px;
            width: 200px;
        }

        input[type="submit"] {
            margin-top: 20px;
            padding: 8px 20px;
        }
    </style>
    <script>
        function toggleAccountForm(checkbox, formId) {
            document.getElementById(formId).style.display = checkbox.checked ? 'block' : 'none';
        }
    </script>
</head>
<body>

<h1>Register Customer</h1>

<form action="${pageContext.request.contextPath}/admin/registerCustomer" method="post">
    <div class="form-group">
        <label for="firstName">First Name:</label>
        <input type="text" name="firstName" id="firstName" required>
    </div>

    <div class="form-group">
        <label for="lastName">Last Name:</label>
        <input type="text" name="lastName" id="lastName" required>
    </div>

    <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" name="email" id="email" required>
    </div>

    <div class="form-group">
        <label for="phoneNumber">Phone:</label>
        <input type="text" name="phoneNumber" id="phoneNumber" required>
    </div>

    <div class="form-group">
        <label for="dob">Date of Birth:</label>
        <input type="date" name="dob" id="dob" required>
    </div>

    <div class="form-group">
        <label for="address">Address:</label>
        <input type="text" name="address" id="address" required>
    </div>

    <div>
        <label for="savingInitialDeposit">Initial Deposit Saving Account:</label>
        <input type="number" name="savingInitialDeposit" id="savingInitialDeposit" min="0">
    </div>

    <br/>
    <br/>
    <br/>

    <div class="form-group">
        <input type="checkbox" name="createFixed" id="createFixed"
               onclick="toggleAccountForm(this, 'fixedAccountForm')">
        <label for="createFixed">Fixed Account</label>
    </div>

    <div id="fixedAccountForm" class="account-section">
        <h4>Fixed Account Details</h4>
        <div class="form-group">
            <label for="fixedInitialDeposit">Initial Deposit:</label>
            <input type="number" name="fixedInitialDeposit" id="fixedInitialDeposit" min="0">
        </div>
    </div>

    <input type="submit" value="Register">
</form>

</body>
</html>
