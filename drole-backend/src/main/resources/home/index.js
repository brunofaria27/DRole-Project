window.onload = () => {
    event.preventDefault();
    let obj = JSON.parse(localStorage.getItem("currentUser"));
    let user_type = obj.userType;
  
    // Se o usuário não for do tipo frequentador poderá ver o link para perfil
    if (user_type == 2 || user_type == 4 || user_type == 3) {
      $('<a class="nav-link" href="../profile/index.html">Perfil</a>').appendTo(
        "#showProfile"
      );
    }
  };
  