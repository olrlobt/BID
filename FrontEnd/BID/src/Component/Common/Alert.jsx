import Swal from "sweetalert2";

export default function alertBtn({ title, text }) {
  Swal.fire({
    title,
    text,
    confirmButtonColor: "#ffd43a",
    icon: "warning",
  });
}
