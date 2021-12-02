document
  .getElementById("btn-modal-avancar")
  .addEventListener("click", showMusicians);
document
  .getElementById("btn-modal2-cancelar")
  .addEventListener("click", function (e) {
    document.getElementById("si-div").innerHTML = `
    <label id="txtModal" for="siEvento" class="col-sm-2 col-form-label">
      <button data-bs-toggle="tooltip" data-bs-placement="top" title="Recomendação"
          id="btn-si" type="button" class="btn-event"><img id="btn-si-img"
              src="../images/foward.png" alt="Recomendação" width="100%"
              height="50px"></button>
    </label>
    <div class="col-sm-10" style="margin: auto;">
      <p style="margin: 0;">Exibir estilo musical recomendado</p>
    </div>
    `;
  });
document
  .getElementById("btn-modal-salvar")
  .addEventListener("click", createEvent);
document
  .getElementById("btn-modal-avancar")
  .addEventListener("click", getValuesForm);
document
  .getElementById("btn-si")
  .addEventListener("click", getSistemaInteligente);

window.onload = () => {
  window.event.preventDefault();
  showCurrentUserProfile();

  document.getElementById("user-logout").addEventListener("click", logout);

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
      `<button data-bs-toggle="tooltip" data-bs-placement="top" title="Adicionar Evento" class="btn-event" type="button" value="Adicionar evento" id="btnInsert" data-toggle="modal" data-target="#calendarioModal">
                      <img id="btn-accept-img" src="../images/AddEvento.gif" alt="Adicionar Evento"></button>`
    ).appendTo("#buttonAddEvent");
  }

  // Se o usuário não for do tipo frequentador poderá ver o link para perfil
  if (user_type == 2 || user_type == 4 || user_type == 3) {
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

  if (event_name == "" || event_musician_id == null || date_event == "") {
    alert("Preencha todos os campos antes de criar um evento!");
  } else {
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
}

function showEvents(filtro) {
  window.event.preventDefault();

  let current_id = JSON.parse(localStorage.getItem("currentUser")).id;
  let userType = JSON.parse(localStorage.getItem("currentUser")).userType;

  if (filtro == "Recusados") {
    document.getElementById("table_title").src =
      "../images/títuloRecusados.png";
  } else {
    document.getElementById("table_title").src =
      "../images/títuloConfirmados.png";
  }

  if (userType == 2 || userType == 3 || userType == 4) {
    $(`<div class="col-sm-12">
    <table id="grid-events1" class="table table-striped">
        <img id="pending-table-title" class="tituloSec" src="../images/títuloPendentes.png" alt="Eventos Pendentes">
        <thead>
            <tr>
                <th scope="col">Nome</th>    
                <th scope="col">Dia</th>
                <th scope="col">Estabelecimento</th>
                <th scope="col">Músico</th>
                <th scope="col">Tipo músical</th>
                <th scope="col">Entrada</th>
                <th id="status-th" scope="col">Status</th>
            </tr>
        </thead>
        <tbody id="table-pending-events">
        </tbody>
    </table>`).appendTo("#div-first-table-events");
  }

  var this_pendents = 0;
  var this_events = 0;

  $.ajax({
    type: "GET",
    url: "http://localhost:4568/events/data",
    dataType: "xml",
    success: function (xml) {
      $(xml)
        .find("event")
        .each(function () {
          this_events++;

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
              if (current_id == musicianId) {
                this_pendents++;
                $(`<tr>
                      <td id="td-eventName-${eventId}" scope="row"> ${eventName}</td>
                      <td scope="row"> ${date}</td>
                      <td scope="row"> ${hostName}</td>
                      <td scope="row"> ${musicianName}</td>
                      <td scope="row"> ${musicalStyle}</td>
                      <td scope="row"> ${entrance}</td>
                      <td scope="row"> <button data-bs-toggle="tooltip" data-bs-placement="top" title="Aceitar Evento" class="btn-event" type="button" value="${eventId}" id="btn-accept-event-${eventId}">
                      <img id="btn-accept-img" src="../images/check.png" alt="Aceitar Evento" width="30px" height="30px"></button>
                                       <button data-bs-toggle="tooltip" data-bs-placement="top" title="Recusar Evento" class="btn-event" type="button" value="${eventId}" id="btn-deny-event-${eventId}" >
                      <img id="btn-deny-img" src="../images/X.png" alt="Recusar Evento" width="30px" height="30px"></button> </td>
                      
                    </tr>`).appendTo("#table-pending-events");

                var btnId = "btn-accept-event-" + eventId;
                document
                  .getElementById(btnId)
                  .addEventListener("click", function (e) {
                    UpdateEventStatus(eventId, "Aceito");
                  });

                var btnId2 = "btn-deny-event-" + eventId;
                document
                  .getElementById(btnId2)
                  .addEventListener("click", function (e) {
                    UpdateEventStatus(eventId, "Recusado");
                  });
              }
            } else if (userType == 2) {
              if (current_id == hostId) {
                this_pendents++;
                $(`<tr>
                    <td id="td-eventName-${eventId}" scope="row"> ${eventName}</td>
                    <td scope="row"> ${date}</td>
                    <td scope="row"> ${hostName}</td>
                    <td scope="row"> ${musicianName}</td>
                    <td scope="row"> ${musicalStyle}</td>
                    <td scope="row"> ${entrance}</td>
                    <td scope="row"><img data-bs-toggle="tooltip" data-bs-placement="top" title="Aguardando Resposta do Músico" id="btn-pendent-img" src="../images/interrogacao.png" alt="" width="30px" height="30px">
                   </tr>`).appendTo("#table-pending-events");
              }
            }
          } else if (filtro == "Todos") {
            if (event_status == "Aceito") {
              $(`<tr>
                  <td id="td-eventName-${eventId}" scope="row">${eventName}</td>
                  <td scope="row"> ${date}</td>
                  <td scope="row"> ${hostName}</td>
                  <td scope="row"> ${musicianName}</td>
                  <td scope="row"> ${musicalStyle}</td>
                  <td scope="row"> ${entrance}</td>
                  </tr>`).appendTo("#table-events");
            }
          } else if (filtro == "Recusados") {
            if (current_id == hostId) {
              if (event_status == "Recusado") {
                $(`<tr>
                  <td id="td-eventName-${eventId}" scope="row"> ${eventName}</td>
                  <td scope="row"> ${date}</td>
                  <td scope="row"> ${hostName}</td>
                  <td scope="row"> ${musicianName}</td>
                  <td scope="row"> ${musicalStyle}</td>
                  <td scope="row"> ${entrance}</td>
                  </tr>`).appendTo("#table-events");
              }
            }
          } else if (musicalStyle == filtro) {
            $(`<tr>
                <td id="td-eventName-${eventId}" scope="row"> ${eventName}</td>
                <td scope="row"> ${date}</td>
                <td scope="row"> ${hostName}</td>
                <td scope="row"> ${musicianName}</td>
                <td scope="row"> ${musicalStyle}</td>
                <td scope="row"> ${entrance}</td>
                </tr>`).appendTo("#table-events");
          }

          if (current_id == hostId) {
            $("#td-eventName-" + eventId)
              .append(`<button data-bs-toggle="tooltip" data-bs-placement="top" title="Deletar Evento" class="btn-event-delete" type="button" value="${eventId}" id="btn-delete-event-${eventId}" >
            <img id="btn-delete-img" src="../images/Deletar.png" alt="Deletar Evento" width="30px" height="30px"></button>`);
            var btnId3 = "btn-delete-event-" + eventId;
            document
              .getElementById(btnId3)
              .addEventListener("click", function (e) {
                DeleteEvent(eventId);
              });
          }
        });
      if (this_pendents == 0) {
        document.getElementById("div-first-table-events").innerHTML = "";
      }
    },
    error: function () {
      if (this_pendents == 0) {
        document.getElementById("div-first-table-events").innerHTML = "";
      }
      if (this_events == 0) {
        $(`<tr>
                <td scope="row"> Nenhum evento disponível.</td>
                </tr>`).appendTo("#table-events");
      } else {
        alert("Ocorreu um erro inesperado durante o processamento.");
      }
    },
  });
}

function DeleteEvent(eventId) {
  window.event.preventDefault();

  $.ajax({
    url: "http://localhost:4568/events/" + eventId,
    datatype: "json",
    method: "DELETE",
  })
    .done(function (data) {
      window.location.reload();
    })
    .fail(function (data) {
      alert("Falha ao deletar o evento!");
    });
}

function UpdateEventStatus(eventId, status) {
  window.event.preventDefault();

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

function getValuesForm() {
  var valueCapacity = document.getElementById("event_capacity").value;
  var valueFormality = document.getElementById("event_formality").value;
  var valueTarget = document.getElementById("event_target").value;
  var valueHour = document.getElementById("event_hour").value;
  var valuePrice = document.getElementById("event_price").value;

  $.ajax({
    url: "http://localhost:4568/events/musicians",
    method: "POST",
    data: {
      event_capacity: valueCapacity,
      event_formality: valueFormality,
      event_target: valueTarget,
      event_hour: valueHour,
      event_price: valuePrice,
    },
    success: function (data) {},
    error: function () {
      alert("Ocorreu um erro inesperado durante o processamento.");
    },
  });
}

function getSistemaInteligente() {
  var valueCapacity = document.getElementById("event_capacity").value;
  var valueFormality = document.getElementById("event_formality").value;
  var valueTarget = document.getElementById("event_target").value;
  var valueHour = document.getElementById("event_hour").value;
  var valuePrice = document.getElementById("event_price").value;

  $.ajax({
    type: "POST",
    url: "http://localhost:4568/events/create/si",
    datatype: "json",
    data: {
      event_capacity: valueCapacity,
      event_formality: valueFormality,
      event_target: valueTarget,
      event_hour: valueHour,
      event_price: valuePrice,
    },
  }).done(function (data) {
    document.getElementById("si-div").innerHTML = `
    <div id="notificationSi" class="col-sm-12" style="margin: auto;">
      <p style="margin: 0;">Estilo musical recomendado para o evento: <strong>${
        data === "Axe" ? "Axé" : data
      }</strong></p>
    </div>
    `;
  });
}

document
  .getElementById("btn-save-event")
  .addEventListener("click", function (e) {
    if (
      document.getElementById("event_name").value == null ||
      document.getElementById("date_event").value == "" ||
      document.getElementById("musical_style").value == "" ||
      document.getElementById("musicians").value == "" ||
      document.getElementById("minimum_age").value == ""
    ) {
      alert("campo vazio");
      document.getElementById("btn-save-event").disabled = true;
    } else {
      document.getElementById("btn-save-event").disabled = false;
    }
  });
