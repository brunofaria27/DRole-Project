//document.getElementById("btn-card-profile").addEventListener("click", showProfile);

window.onload = () => {
  window.event.preventDefault();
  let obj = JSON.parse(localStorage.getItem("currentUser"));
  let user_type = obj.userType;

  // Se o usuário não for do tipo frequentador poderá ver o link para perfil
  if (user_type == 2 || user_type == 4 || user_type == 3) {
    $('<a class="nav-link" href="../profile/index.html">Perfil</a>').appendTo(
      "#showProfile"
    );
  }

  showProfiles();
};

function showProfiles() {
  window.event.preventDefault();

  $.ajax({
    type: "GET",
    url: "http://localhost:4568/user/",
    dataType: "xml",
    success: function (xml) {
      $(xml)
        .find("user")
        .each(function () {
          var name = $(this).find("profileName").text();
          var id = $(this).find("id").text();
          var type = $(this).find("userType").text();
          var description = $(this).find("description").text();
          var photo_path = $(this).find("userPhoto").text();

          if(type == 2) type = 'Estabelecimento';
          else if(type == 3) type = 'Músico';
          else if(type == 4) type = 'ADM';

          if (name != "null") {
            $(`
            <div class="card col-mb-4" style="width: 18rem; height:40rem; border-radius:2%">
                <img src="${photo_path}" class="card-img-top" alt="..." style="width: 100%;height: 15vw;object-fit: cover;">
                <div class="card-body">
                    <h5 class="card-title">${name}</h5>
                    <p class="card-text">${type}</p>
                    <p class="card-text">${description}</p>
                    <p class="card-text">Avaliação</li>
                </div>
                <div class="card-body text-center">
                    <button id="btn-card-profile" value="${id}" type="button" class="btn btn-warning">Visitar Perfil</button>
            </div>`
            ).appendTo("#profiles-grid");
          }
        });
    },
    error: function () {
      alert("Ocorreu um erro inesperado durante o processamento.");
    },
  });
}

function showProfile() {
  window.event.preventDefault();

  let profile_id = document.getElementById("btn-card-profile").value

  // $.ajax({
  //   type: "GET",
  //   url: "http://localhost:4568/user",
  //   dataType: "xml",
  //   success: function (xml) {
  //     $(xml)
  //       .find("user")
  //       .each(function () {
  //         var name = $(this).find("profileName").text();
  //         var id = $(this).find("id").text();
  //         var type = $(this).find("userType").text();
  //         var description = $(this).find("description").text();
  //         var photo_path = $(this).find("userPhoto").text();

  //         if(type == 2) type = 'Estabelecimento';
  //         else if(type == 3) type = 'Músico';

  //         if (name != "null") {
  //           $(`
  //           <div class="card col-2" style="width: 18rem;">
  //               <img src="${photo_path}" class="card-img-top" alt="...">
  //               <div class="card-body">
  //                   <h5 class="card-title">${name}</h5>
  //                   <p class="card-text">${type}</p>
  //                   <p class="card-text">${description}</p>
  //               </div>
  //               <ul class="list-group list-group-flush">
  //                   <li class="list-group-item">Avaliação</li>
  //               </ul>
  //               <div class="card-body">
  //                   <button id="btn-card-profile" value="${id}" type="button" class="btn btn-dark">Visitar Perfil</button>
  //               </div>
  //           </div>`
  //           ).appendTo("#show_profles");
  //         }
  //       });
  //   },
  //   error: function () {
  //     alert("Ocorreu um erro inesperado durante o processamento.");
  //   },
  // });
}
