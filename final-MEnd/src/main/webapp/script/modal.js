// When the user clicks on the button, open the modal 
function accept(){
	// event.srcElement.parentNode.querySelector(".modal-wrapper-accept")
	event.srcElement.parentNode.querySelector(".modal-wrapper-accept").style.display = "block";
}

function reject() {
	event.srcElement.parentNode.querySelector(".modal-wrapper-reject").style.display = "block";
}

// When the user clicks on <span> (x), close the modal
let arr = document.getElementsByClassName("close");
for(i = 0; i < arr.length; i++){
	arr[i].addEventListener(("click"), close);
}

function close(){
	console.log("action close");
	this.parentNode.parentNode.style.display = 'none';
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
	if (event.target.classList.contains('modal-wrapper-accept') ||
		event.target.classList.contains('modal-wrapper-reject')) {
		event.target.style.display = "none";
	}
}




















