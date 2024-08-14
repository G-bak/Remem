

function updateTimer() {
    const future = Date.parse("2024/09/01 00:00:00");
 const now = new Date();
 const diff = future - now;

 const days = Math.floor(diff / (1000 * 60 * 60 * 24));
 const hours = Math.floor(diff / (1000 * 60 * 60));
 const mins = Math.floor(diff / (1000 * 60));
 const secs = Math.floor(diff / 1000);

 const d = days;
 const h = hours - days * 24;
 const m = mins - hours * 60;
 const s = secs - mins * 60;
 document.getElementById("timer")
  .innerHTML =
  '<div>' + d + '<span>Days</span></div>' +
  '<div>' + h + '<span>Hours</span></div>' +
  '<div>' + m + '<span>Minutes</span></div>' +
  '<div>' + s + '<span>Seconds</span></div>';
}

setInterval(updateTimer, 1000);