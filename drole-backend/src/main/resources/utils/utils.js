
function logout() {
  localStorage.removeItem("currentUser");
  let newURl =
    window.location.protocol + "//" + window.location.host + "/" + "login/";
  window.location.href = newURl;
}


// Se o usuário não for do tipo frequentador poderá ver o link para perfil
function showCurrentUserProfile(){
  let user = JSON.parse(localStorage.getItem("currentUser"))
  let user_type = user.userType
    if (user_type == 2 || user_type == 4 || user_type == 3) {
        $("#showProfile").prepend(
          `<a class="dropdown-item" href="../profile/index.html">Perfil</a>`
        );
      }
}
