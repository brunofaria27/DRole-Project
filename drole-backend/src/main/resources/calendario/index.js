document
  .getElementById("btn-prox-modal")
  .addEventListener("click", showMusicians);
document
  .getElementById("btn-save-event")
  .addEventListener("click", createEvent);

window.onload = () => {
  window.event.preventDefault();
  showEvents();

  let obj = JSON.parse(localStorage.getItem("currentUser"));
  let user_type = obj.userType;

  // Se o usuário for do tipo estabelecimento ou adm poderá ver o botão de adicionar evento
  if (user_type == 2 || user_type == 4) {
    $(
      '<input type="button" class="btn btn-success" id="btnInsert" value="Adicionar evento" data-toggle="modal" data-target="#calendarioModal">'
    ).appendTo("#buttonAddEvent");
  }

  // Se o usuário não for do tipo frequentador poderá ver o link para perfil
  if (user_type == 2 || user_type == 4 || user_type == 3) {
    $(
      '<a class="nav-link" href="../profile/index.html">Perfil</a>'
    ).appendTo("#showProfile");
  }

};


function showMusicians() {
  window.event.preventDefault();

  $.ajax({
    type: "GET",
    url: "http://localhost:4568/events/musicians",
    dataType: "xml",
    success: function (xml) {
      $(xml)
        .find("user")
        .each(function () {
          var name = $(this).find("profileName").text();
          var value = $(this).find("id").text();

          if (name != "null") {
            $(
              '<option id="event_musician_id" value="' +
                value +
                '">' +
                name +
                "</option>"
            ).appendTo("#musicians");
          }
        });
    },
    error: function () {
      alert("Ocorreu um erro inesperado durante o processamento.");
    },
  });
}

function createEvent() {
  window.event.preventDefault();

  let event_name = document.getElementById("event_name").value;
  let event_musician_id = document.getElementById("musicians").value;
  let musical_style = document.getElementById("musical_style").value;
  let minimum_age = document.getElementById("minimum_age").value;
  let event_host_id = JSON.parse(localStorage.getItem("currentUser")).id;
  let event_status = "Pendente";
  let date_event = document.getElementById("date_event").value;
  let event_capacity = document.getElementById("event_capacity").value;
  let event_formality = document.getElementById("event_formality").value;
  let event_target = document.getElementById("event_target").value;
  let event_hour = document.getElementById("event_hour").value;
  let event_price = document.getElementById("event_price").value;

  $.ajax({
    url: "http://localhost:4568/events/create",
    method: "POST",
    data: {
      event_name: event_name,
      event_musician_id: event_musician_id,
      musical_style: musical_style,
      minimum_age: minimum_age,
      event_host_id: event_host_id,
      event_status: event_status,
      date_event: date_event,
      event_capacity: event_capacity,
      event_formality: event_formality,
      event_target: event_target,
      event_hour: event_hour,
      event_price: event_price,
    },
    success: function (data) {
      window.location = "../calendario/index.html";
    },
    error: function () {
      alert("Ocorreu um erro inesperado durante o processamento.");
    },
  });
}

function showEvents() {
  window.event.preventDefault();

  let current_id = JSON.parse(localStorage.getItem("currentUser")).id;

  $.ajax({
    type: "GET",
    url: "http://localhost:4568/events/data",
    dataType: "xml",
    success: function (xml) {
      $(xml)
        .find("event")
        .each(function () {
          var date = $(this).find("date").text();
          var musicianName = $(this).find("musicianName").text();
          var hostName = $(this).find("hostName").text();
          var hostId = $(this).find("hostId").text();
          var musicianId = $(this).find("musicianId").text();
          var musicalStyle = $(this).find("musicalStyle").text();
          var minimumAge = $(this).find("minimumAge").text();
          var entrance;
          if(minimumAge == 1){
            entrance = '18+'
          }else{
            entrance = '18-'
          }

          $(`<tr>
          <td scope="row"> ${date}</td>
          <td scope="row"> ${hostName}</td>
          <td scope="row"> ${musicianName}</td>
          <td scope="row"> ${musicalStyle}</td>
          <td scope="row"> ${entrance}</td>
          `).appendTo("#table-events");

          if(current_id == hostId){
            console.log("cheguei");
            $(`</tr>`).appendTo("#table-events");
          }
          else if(current_id == musicianId){
            $(`</tr>`).appendTo("#table-events");
          }
          else{
            $(`</tr>`).appendTo("#table-events");
          }
        });
    },
    error: function () {
      alert("Ocorreu um erro inesperado durante o processamento.");
    },
  });
}
