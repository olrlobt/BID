import styled from './SmallEvent.module.css';
import { Views } from 'react-big-calendar';

export default function SmallEvent({ date }) {
  console.log(date);

  return (
    <section className={styled.container}>
      <div>hi</div>
      <button>뒤로가자</button>
    </section>
  );
}
