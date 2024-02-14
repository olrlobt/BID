import Swal from 'sweetalert2';

export default function confirmBtn({
  icon,
  title,
  text,
  confirmTxt,
  confirmColor,
  cancelTxt,
  cancelColor,
  confirmFunc,
  cancelFunc,
}) {
  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: 'btn btn-success',
      cancelButton: 'btn btn-danger',
    },
    buttonsStyling: true,
  });
  swalWithBootstrapButtons
    .fire({
      title,
      text,
      icon,
      showCancelButton: true,
      confirmButtonText: confirmTxt,
      confirmButtonColor: confirmColor,
      cancelButtonText: cancelTxt,
      cancelButtonColor: cancelColor,
    })
    .then((result) => {
      if (result.isConfirmed) {
        confirmFunc();
      } else if (result.isDismissed) {
        cancelFunc();
      }
    });
}
