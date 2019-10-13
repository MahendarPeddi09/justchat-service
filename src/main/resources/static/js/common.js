function toggle_emoji_holder() {
    var x = document.getElementById("emoji_placeholder");
    if (x.style.display === "none") {
      x.style.display = "block";
    } else {
      x.style.display = "none";
    }
  }
  function toggle_user_details() {
    var x = document.getElementById("chat_window_b");
    if (x.style.display === "none") {
      x.style.display = "block";
    } else {
      x.style.display = "none";
    }
  }
  function close_user_details() {
    var x = document.getElementById("chat_window_b");

      x.style.display = "none";
  }
  function toggle_chat_conversation_search() {
    var x = document.getElementById("chat_window_header_search_input");
    if (x.style.display === "none") {
      x.style.display = "block";
      
    } else {
      x.style.display = "none";
    }
  }


