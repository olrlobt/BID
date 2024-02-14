import Swal from "sweetalert2";

export default function confirmBtn({
  title,
  text,
  confirmTxt,
  confirmColor,
  cancelTxt,
  cancelColor,
}) {
  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success",
      cancelButton: "btn btn-danger",
    },
    buttonsStyling: true,
  });
  const result = swalWithBootstrapButtons.fire({
    title,
    text,
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: confirmTxt,
    confirmButtonColor: confirmColor,
    cancelButtonText: cancelTxt,
    cancelButtonColor: cancelColor,
  });

  return result;
}
