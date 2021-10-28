document
  .getElementById("btn-prox-modal")
  .addEventListener("click", showMusicians);
document
  .getElementById("btn-save-event")
  .addEventListener("click", createEvent);

function showMusicians() {
  event.preventDefault();

  $.ajax({
    type: "GET",
    url: "http://localhost:4568/eventos/musicos",
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
  event.preventDefault();

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
      //console.log(data);
    },
    error: function () {
      console.log("nao foi :)");
    },
  });
}
