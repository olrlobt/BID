import Swal from 'sweetalert2';

export default function alertBtn({ title, text, confirmColor, icon }) {
  Swal.fire({
    title,
    text,
    confirmButtonColor: confirmColor,
    icon,
  });
}
