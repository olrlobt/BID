import styled from './SmallEvent.module.css';

export default function SmallEvent({ handleClick, income, outcome }) {
  console.log(handleClick);
  return (
    <section className={styled.container}>
      <div>hi</div>
      <button onClick={handleClick}>뒤로가자</button>
    </section>
  );
}
