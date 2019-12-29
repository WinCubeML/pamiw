// do blokady przycisku submit
var allValid = [0, 0, 0, 0, 0, 0, 0, 0, 0]

// login
var login = document.getElementById("login");
var loginValid = 0;
var timeout = null;
login.addEventListener("keyup", validateLogin);

// hasło
var pass1 = document.getElementById("pass1");
var pass1Valid = 1;
pass1.addEventListener("keyup", validatePassword1)

// powtórz hasło
var pass2 = document.getElementById("pass2");
pass2.disabled = true;
var pass2Valid = 2;
pass2.addEventListener("keyup", validatePassword2)

// imię
var firstName = document.getElementById("firstName");
var firstNameValid = 3;
firstName.addEventListener("focusout", validateFirstName);

// nazwisko
var lastName = document.getElementById("lastName");
var lastNameValid = 4;
lastName.addEventListener("focusout", validateLastName);

// PESEL
var pesel = document.getElementById("pesel");
var peselValid = 5;
pesel.addEventListener("keyup", validatePesel);

// data urodzenia
var birthdate = document.getElementById("birthdate");
var birthValue = "";
var birthdateValid = 6;
birthdate.addEventListener("change", validateBirthdate);

// płeć
var female = document.getElementById("femaleRadio");
var male = document.getElementById("maleRadio");
female.addEventListener("change", validateSex);
male.addEventListener("change", validateSex);
var sexValue = "U";
var sexValid = 7;

// obrazek
var photo = document.getElementById("photo");
photo.addEventListener("change", validatePhoto);
var photoValue = 8;

// submit
var submit = document.getElementById("submitForm");
submit.disabled = true;

// czyszczenie danych
var reset = document.getElementById("resetForm");
reset.addEventListener("click", resetAll)

// funkcja odblokowująca guzik po wypełnieniu formularza
function unlockSubmitButton() {
    for (let i = 0; i < allValid.length; i++) {
        if (allValid[i] != 1) {
            submit.disabled = true;
            return;
        }
    }
    submit.disabled = false;
}

// funkcja czyszcząca formularz
function resetAll() {
    allValid = [0, 0, 0, 0, 0, 0, 0, 0, 0];
    validator = ["validationLogin", "validationFirstName", "validationLastName", "validationPesel", "validationBirthdate", "validationPassword1", "validationPassword2"];
    validator.forEach(function (item) {
        document.getElementById(item).innerHTML = "";
        document.getElementById(item).classList.remove("invalid");
        document.getElementById(item).classList.remove("valid");
        document.getElementById(item).classList.remove("valid-warn");
        sexValue = "U";
        birthValue = new Date();
    });
    unlockSubmitButton();
}

// walidacja loginu z opóźnieniem
function validateLogin() {
    var validator = document.getElementById("validationLogin");
    makeValidWarn(validator)
    validator.innerHTML = "Oczekiwanie na zakończenie pisania..."

    clearTimeout(timeout);
    timeout = setTimeout(function () {
        var x = document.forms["form"]["login"].value;
        var link = "https://pi.iem.pw.edu.pl/user/" + x;
        fetch(link).then(resp => {
            if (resp.status == 404) {
                validator.innerHTML = "Nazwa użytkownika jest wolna.";
                makeValid(validator);
                allValid[loginValid] = 1;
            } else if (resp.status == 200) {
                validator.innerHTML = "Nazwa użytkownika zajęta. Proszę wybrać inną.";
                makeInvalid(validator);
                allValid[loginValid] = 0;
            }
        }).catch(error => {
            validator.innerHTML = "Wystąpił błąd przy próbie połączenia się ze stroną."
            makeInvalid(validator);
            allValid[loginValid] = 0;
        })
        unlockSubmitButton();
    }, 1000);
}

// walidacja hasła
function validatePassword1() {
    var lowercase = /[a-z]/g
    var uppercase = /[A-Z]/g

    var x = document.forms["form"]["pass1"].value;
    var validator = document.getElementById("validationPassword1");
    if (!(x.match(lowercase) && x.match(uppercase) && x.length >= 8)) {
        validator.innerHTML = "Hasło powinno składać się z co najmniej 8 znaków i zawierać wyłącznie małe i wielkie litery angielskiego alfabetu."
        makeInvalid(validator);
        document.getElementById("pass2").value = "";
        document.getElementById("pass2").disabled = true;
        allValid[pass1Valid] = 0;
        allValid[pass2Valid] = 0;
    } else {
        validator.innerHTML = "Hasło dostatecznie silne."
        makeValid(validator);
        document.getElementById("pass2").disabled = false;
        allValid[pass1Valid] = 1;
        validatePassword2()
    }
    unlockSubmitButton();
}

// sprawdzenie, czy hasła są tożsame
function validatePassword2() {
    var pass1 = document.forms["form"]["pass1"].value;
    var pass2 = document.forms["form"]["pass2"].value;

    var validator = document.getElementById("validationPassword2");
    if (pass1 == pass2) {
        validator.innerHTML = "Hasła są zgodne.";
        makeValid(validator);
        allValid[pass2Valid] = 1;
    } else {
        validator.innerHTML = "Hasła nie są zgodne.";
        makeInvalid(validator);
        allValid[pass2Valid] = 0;
    }
    unlockSubmitButton();
}

// walidacja imienia
function validateFirstName() {
    var x = document.forms["form"]["firstName"].value;
    var validator = document.getElementById("validationFirstName");
    if (x == "") {
        validator.innerHTML = "To pole jest wymagane."
        makeInvalid(validator);
        allValid[firstNameValid] = 0;
    } else if (!x.match(/^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$/)) {
        validator.innerHTML = "Imię powinno zaczynać się wielką literą, po której powinny następować małe. Może zawierać wyłącznie litery polskiego alfabetu."
        makeInvalid(validator);
        allValid[firstNameValid] = 0;
    } else {
        validator.innerHTML = "Imię jest poprawne.";
        validator.classList.remove("invalid");
        validator.classList.add("valid");
        allValid[firstNameValid] = 1;
    }
    unlockSubmitButton();
}

// walidacja nazwiska
function validateLastName() {
    var x = document.forms["form"]["lastName"].value;
    var validator = document.getElementById("validationLastName");
    if (x == "") {
        validator.innerHTML = "To pole jest wymagane."
        makeInvalid(validator);
        allValid[lastNameValid] = 0;
    } else if (!x.match(/^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$/)) {
        validator.innerHTML = "Nazwisko powinno zaczynać się wielką literą, po której powinny następować małe. Może zawierać wyłącznie litery polskiego alfabetu."
        makeInvalid(validator);
        allValid[lastNameValid] = 0;
    } else {
        validator.innerHTML = "Nazwisko jest poprawne.";
        validator.classList.remove("invalid");
        validator.classList.add("valid");
        allValid[lastNameValid] = 1;
    }
    unlockSubmitButton();
}

// walidacja peselu w czasie rzeczywistym
function validatePesel() {
    var x = document.forms["form"]["pesel"].value;
    var validator = document.getElementById("validationPesel");
    if (x == "") {
        validator.innerHTML = "To pole jest wymagane."
        makeInvalid(validator);
        allValid[peselValid] = 0;
        chooseSexForPesel(false, null);
        chooseBirthdateForPesel(false, null);
        unlockSubmitButton();
        return;
    } else if (!x.match(/^[0-9]+$/) || x.length != 11) {
        validator.innerHTML = "PESEL składa się wyłącznie z 11 cyfr."
        makeInvalid(validator);
        allValid[peselValid] = 0;
        chooseSexForPesel(false, null);
        chooseBirthdateForPesel(false, null);
        unlockSubmitButton();
        return;
    }

    var weight = [1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 1]
    let sum = 0;
    for (let i = 0; i < weight.length; i++) {
        sum += (parseInt(x.substring(i, i + 1)) * weight[i]);
    }
    sum %= 10;
    if (sum == 0) {
        validator.innerHTML = "PESEL jest poprawny.";
        validator.classList.remove("invalid");
        validator.classList.add("valid");
        allValid[peselValid] = 1;
        chooseSexForPesel(true, parseInt(x.substring(9, 10)));
        chooseBirthdateForPesel(true, x.substring(0, 6));
    } else {
        validator.innerHTML = "Podany PESEL jest nieprawidłowy."
        makeInvalid(validator);
        allValid[peselValid] = 0;
        chooseSexForPesel(false, null);
        chooseBirthdateForPesel(false, null);
    }
    unlockSubmitButton();
}

// automatyczny wybór płci
function chooseSexForPesel(valid, num) {
    if (valid === false) {
        allValid[sexValid] = 0;
        document.getElementById("femaleRadio").checked = false;
        document.getElementById("maleRadio").checked = false;
        document.getElementById("validationSex").innerHTML = "";
        sexValue = "U";
    } else {
        allValid[sexValid] = 1;
        if (num % 2 == 0) {
            document.getElementById("femaleRadio").checked = true;
            document.getElementById("maleRadio").checked = false;
            document.getElementById("validationSex").innerHTML = "";
            sexValue = "F";
        } else {
            document.getElementById("femaleRadio").checked = false;
            document.getElementById("maleRadio").checked = true;
            document.getElementById("validationSex").innerHTML = "";
            sexValue = "M";
        }
    }
}

// automatyczny wybór daty urodzenia
function chooseBirthdateForPesel(valid, date) {
    if (valid === false) {
        allValid[birthdateValid] = 0;
        document.getElementById("birthdate").value = "";
        validator = document.getElementById("validationBirthdate");
        validator.innerHTML = "";
        birthValue = "";
        removeValidInvalid(validator)
    } else {
        allValid[birthdateValid] = 1;
        var year = parseInt(date.substring(0, 2));
        var month = parseInt(date.substring(2, 4));
        var day = parseInt(date.substring(4, 6));
        if (month > 12) {
            month -= 21;
            year += 2000
        } else {
            month -= 1;
            year += 1900;
        }
        var dateFromPesel = new Date()
        dateFromPesel.setDate(day);
        dateFromPesel.setMonth(month);
        dateFromPesel.setFullYear(year);
        document.getElementById("birthdate").valueAsDate = dateFromPesel;
        birthValue = dateFromPesel;

        validator = document.getElementById("validationBirthdate");
        validator.innerHTML = "";
        removeValidInvalid(validator)
    }
}

// walidacja wprowadzonej przez użytkownika daty urodzenia
function validateBirthdate() {
    var x = new Date(document.forms["form"]["birthdate"].value);
    var teraz = new Date(Date.now());
    var validator = document.getElementById("validationBirthdate");

    if (x.getFullYear() < 1900) {
        validator.innerHTML = "Data nie może być wcześniejsza niż rok 1900."
        makeInvalid(validator);
        allValid[birthdateValid] = 0;
    } else if (x.getTime() > teraz.getTime()) {
        validator.innerHTML = "Data nie może być późniejsza niż dzisiejsza."
        makeInvalid(validator);
        allValid[birthdateValid] = 0;
    } else {
        if (x.getFullYear() == birthValue.getFullYear() &&
            x.getMonth() == birthValue.getMonth() &&
            x.getDate() == birthValue.getDate()) {
            validator.innerHTML = "";
            removeValidInvalid(validator);
        } else {
            validator.innerHTML = "Data nie pokrywa się z PESELem.";
            makeValidWarn(validator);
        }
        allValid[birthdateValid] = 1;
    }
    unlockSubmitButton();
}

// walidacja wprowadzonej płci przez użytkownika
function validateSex() {
    var pesel = document.getElementById("validationPesel");
    var validator = document.getElementById("validationSex");
    allValid[sexValid] = 1;
    if (pesel.classList.contains("valid")) {
        if (parseInt(document.getElementById("pesel").value.substring(9, 10)) % 2 == 0) {
            if (document.getElementById("maleRadio").checked == true) {
                validator.innerHTML = "Płeć nie pokrywa się z PESELem."
                makeValidWarn(validator);
            } else {
                validator.innerHTML = "";
                removeValidInvalid(validator);
            }
        } else {
            if (document.getElementById("femaleRadio").checked == true) {
                validator.innerHTML = "Płeć nie pokrywa się z PESELem."
                makeValidWarn(validator);
            } else {
                validator.innerHTML = "";
                removeValidInvalid(validator);
            }
        }
    }
    unlockSubmitButton();
}

// sprawdzenie rozmiaru obrazka
function validatePhoto() {
    var photo = document.getElementById("photo");
    var file = photo.files[0];
    var validator = document.getElementById("validationPhoto");
    if (file.size > 1048576) {
        validator.innerHTML = "Plik nie może przekraczać 1 MB."
        makeInvalid(validator);
        allValid[photoValue] = 0;
        photo.value = "";
    } else {
        validator.innerHTML = "Zdjęcie wgrane."
        makeValid(validator)
        allValid[photoValue] = 1;
    }
    unlockSubmitButton();
}

function makeInvalid(validator) {
    validator.classList.remove("valid");
    validator.classList.remove("valid-warn");
    validator.classList.add("invalid");
}

function makeValidWarn(validator) {
    validator.classList.remove("valid");
    validator.classList.remove("invalid");
    validator.classList.add("valid-warn");
}

function makeValid(validator) {
    validator.classList.remove("valid-warn");
    validator.classList.remove("invalid");
    validator.classList.add("valid");
}

function removeValidInvalid(validator) {
    validator.classList.remove("valid");
    validator.classList.remove("valid-warn");
    validator.classList.remove("invalid");
}