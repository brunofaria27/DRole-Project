document
  .getElementById("btn-prox-modal")
  .addEventListener("click", showMusicians);
document
  .getElementById("btn-save-event")
  .addEventListener("click", createEvent);

window.onload = () => {
  window.event.preventDefault();
  document
    .getElementById("filter-musical-style")
    .addEventListener("change", function () {
      document.getElementById("table-events").innerHTML = "";


      let tablePendentes = document.getElementById("table-pending-events");
      if (tablePendentes) tablePendentes.remove();

      let tituloPendente = document.getElementById("pending-table-title");
      if (tituloPendente) tituloPendente.remove();

      let rowsPendente = document.getElementById("grid-events1");
      if (rowsPendente) rowsPendente.remove();

      showEvents(document.getElementById("filter-musical-style").value);
    });

  showEvents("Todos");

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
    $('<a class="nav-link" href="../profile/index.html">Perfil</a>').appendTo(
      "#showProfile"
    );
    $(`<option value="Recusados">Apenas Recusados</option>`).appendTo(
      "#filter-musical-style"
    );
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

function showEvents(filtro) {
  window.event.preventDefault();

  let current_id = JSON.parse(localStorage.getItem("currentUser")).id;
  let userType = JSON.parse(localStorage.getItem("currentUser")).userType;

  if(filtro == "Recusados"){
    document.getElementById("table-title").innerHTML = "Eventos Recusados";
  }else{
    document.getElementById("table-title").innerHTML = "Eventos Confirmados";
  }

  if (userType == 2 || userType == 3 || userType == 4) {
    $(`<div class="col-sm-12">
    <table id="grid-events1" class="table table-striped">
        <h1 id="pending-table-title">Eventos Pendentes</h1>
        <thead>
            <tr>
                <th scope="col">Nome</th>    
                <th scope="col">Dia</th>
                <th scope="col">Estabelecimento</th>
                <th scope="col">Músico</th>
                <th scope="col">Tipo músical</th>
                <th scope="col">Entrada</th>
                <th scope="col">Status</th>
            </tr>
        </thead>
        <tbody id="table-pending-events">
        </tbody>
    </table>`).appendTo("#div-first-table-events");
  }

  $.ajax({
    type: "GET",
    url: "http://localhost:4568/events/data",
    dataType: "xml",
    success: function (xml) {
      $(xml)
        .find("event")
        .each(function () {
          var event_status = $(this).find("status").text();
          var eventName = $(this).find("name").text();
          var eventId = $(this).find("id").text();
          var date = $(this).find("date").text();
          var musicianName = $(this).find("musicianName").text();
          var hostName = $(this).find("hostName").text();
          var hostId = $(this).find("hostId").text();
          var musicianId = $(this).find("musicianId").text();
          var musicalStyle = $(this).find("musicalStyle").text();
          var minimumAge = $(this).find("minimumAge").text();
          var entrance;
          if (minimumAge == 1) {
            entrance = "18+";
          } else {
            entrance = "18-";
          }

          if (event_status == "Pendente") {
            if (userType == 3 || userType == 4) {
              //if (current_id == musicianId) {
              $(`<tr>
                      <td scope="row"> ${eventName}</td>
                      <td scope="row"> ${date}</td>
                      <td scope="row"> ${hostName}</td>
                      <td scope="row"> ${musicianName}</td>
                      <td scope="row"> ${musicalStyle}</td>
                      <td scope="row"> ${entrance}</td>
                      <td scope="row"> <button type="button" value="${eventId}" id="btn-accept-event-${eventId}" class="btn btn-success">Aceitar Evento</button>
                                       <button type="button" value="${eventId}" id="btn-deny-event-${eventId}" class="btn btn-danger">Recusar Evento</button> </td>
                    </tr>`).appendTo("#table-pending-events");

              document.addEventListener("click", function (e) {
                var btnId = "btn-accept-event-" + eventId;
                if (e.target && e.target.id == btnId) {
                  UpdateEventStatus(eventId, "Aceito");
                }
              });

              document.addEventListener("click", function (e) {
                var btnId = "btn-deny-event-" + eventId;
                if (e.target && e.target.id == btnId) {
                  UpdateEventStatus(eventId, "Recusado");
                }
              });
              //}
            } else if (userType == 2) {
              //if (current_id == hostId) {
              $(`<tr>
                    <td scope="row"> ${eventName}</td>
                    <td scope="row"> ${date}</td>
                    <td scope="row"> ${hostName}</td>
                    <td scope="row"> ${musicianName}</td>
                    <td scope="row"> ${musicalStyle}</td>
                    <td scope="row"> ${entrance}</td>
                    <td scope="row"><span class="badge badge-warning">Aguardando resposta do Músico</span>
                   </tr>`).appendTo("#table-pending-events");
              //}
            }
          } else if (filtro == "Todos") {
            if (event_status == "Aceito") {
              $(`<tr>
                  <td scope="row"> ${eventName}</td>
                  <td scope="row"> ${date}</td>
                  <td scope="row"> ${hostName}</td>
                  <td scope="row"> ${musicianName}</td>
                  <td scope="row"> ${musicalStyle}</td>
                  <td scope="row"> ${entrance}</td>
                  </tr>`).appendTo("#table-events");
            }
          } else if (filtro == "Recusados") {
            //if (current_id == hostId) {
            if (event_status == "Recusado") {
              $(`<tr>
                  <td scope="row"> ${eventName}</td>
                  <td scope="row"> ${date}</td>
                  <td scope="row"> ${hostName}</td>
                  <td scope="row"> ${musicianName}</td>
                  <td scope="row"> ${musicalStyle}</td>
                  <td scope="row"> ${entrance}</td>
                  </tr>`).appendTo("#table-events");
            }
            //}
          } else if (musicalStyle == filtro) {
            $(`<tr>
                <td scope="row"> ${eventName}</td>
                <td scope="row"> ${date}</td>
                <td scope="row"> ${hostName}</td>
                <td scope="row"> ${musicianName}</td>
                <td scope="row"> ${musicalStyle}</td>
                <td scope="row"> ${entrance}</td>
                </tr>`).appendTo("#table-events");
          }
        });
    },
    error: function () {
      alert("Ocorreu um erro inesperado durante o processamento.");
    },
  });
}

function UpdateEventStatus(eventId, status) {
  window.event.preventDefault();

  console.log(eventId + status);

  $.ajax({
    url: "http://localhost:4568/events/" + eventId,
    datatype: "json",
    method: "PUT",
    data: {
      event_status: status,
    },
  })
    .done(function (data) {
      window.location.reload();
    })
    .fail(function (data) {
      alert("Falha ao aceitar/recusar o evento!");
    });
}
