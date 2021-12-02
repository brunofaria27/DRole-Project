window.onload = () => {
  window.event.preventDefault();
  let obj = JSON.parse(localStorage.getItem("currentUser"));
  let user_type = obj.userType;

  showCurrentUserProfile()   
  document.getElementById("user-logout").addEventListener("click", logout);
};
