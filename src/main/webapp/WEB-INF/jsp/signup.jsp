<%@ page contentType="text/html; charset=UTF-8" %>

<html lang="pl">
<head>
    <meta charset="utf-8">
    <title>Rejestracja użytkownika</title>
    <link rel="stylesheet" href="/css/signup.css">
</head>

<body>
<form class="form" id="form" action="https://pi.iem.pw.edu.pl/register" method="POST" enctype="multipart/form-data">
    <div class="formInputText center">
        <label for="login">Login:<br></label>
        <input type="text" placeholder="Wprowadź login" name="login" id="login">
    </div>
    <div id="showLogin" class="validationShow center">
        <p class="validator" id="validationLogin"></p>
    </div>

    <div class="formInputText center">
        <label for="pass1">Hasło:<br></label>
        <input type="password" placeholder="Wprowadź hasło" id="pass1">
    </div>
    <div id="showPassword1" class="validationShow center">
        <p class="validator" id="validationPassword1"></p>
    </div>

    <div class="formInputText center">
        <label for="pass2">Powtórz hasło:<br></label>
        <input type="password" placeholder="Powtórz hasło" name="password" id="pass2">
    </div>
    <div id="showPassword2" class="validationShow center">
        <p class="validator" id="validationPassword2"></p>
    </div>

    <div class="formInputText center">
        <label for="firstName">Imię:<br></label>
        <input type="text" placeholder="Wprowadź imię" name="firstname" id="firstName">
    </div>
    <div id="showFirstName" class="validationShow center">
        <p class="validator" id="validationFirstName"></p>
    </div>

    <div class="formInputText center">
        <label for="lastName">Nazwisko:<br></label>
        <input type="text" placeholder="Wprowadź nazwisko" name="lastname" id="lastName">
    </div>
    <div id="showLastName" class="validationShow center">
        <p class="validator" id="validationLastName"></p>
    </div>

    <div class="formInputText center">
        <label for="pesel">PESEL:<br></label>
        <input type="text" placeholder="Wprowadź PESEL" name="pesel" id="pesel">
    </div>
    <div id="showPesel" class="validationShow center">
        <p class="validator" id="validationPesel"></p>
    </div>

    <div class="formInputDate center">
        <label for="birthdate">Data urodzenia:<br></label>
        <input type="date" name="birthdate" id="birthdate">
    </div>
    <div id="showBirthdate" class="validationShow center">
        <p class="validator" id="validationBirthdate"></p>
    </div>

    <div class="formInputRadio center">
        <label>Płeć:<br></label>

        <label for="femaleRadio">Kobieta</label>
        <input type="radio" id="femaleRadio" value="F" name="sex">
        <br>
        <label for="maleRadio">Mężczyzna</label>
        <input type="radio" id="maleRadio" value="M" name="sex">
    </div>
    <div id="showSex" class="validationShow center">
        <p class="validator" id="validationSex"></p>
    </div>

    <div class="filePhoto center">
        <label for="photo">Zdjęcie (awatar użytkownika):<br></label>
        <input type="file" id="photo" name="photo" accept="image/png, image/jpeg">
    </div>
    <div id="showPhoto" class="validationShow center">
        <p class="validator" id="validationPhoto"></p>
    </div>
    <br>
    <div class="center">
        <button type="submit" id="submitForm" class="btn">Utwórz konto</button>

        <button type="reset" id="resetForm" class="cancelbtn">Wyczyść dane</button>
    </div>
</form>
<script src="/js/signup.js"></script>
</body>
</html>