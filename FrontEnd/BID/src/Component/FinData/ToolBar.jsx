import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faChevronLeft,
  faChevronRight,
  faCalendarDays,
} from "@fortawesome/free-solid-svg-icons";
import styled from "./ToolBar.module.css";

export default function Toolbar(props) {
  const { date } = props;

  const navigate = (action) => {
    props.onNavigate(action);
  };

  const onViews = (action) => {
    props.onView(action);
  };

  return (
    <div className={styled.rbc_toolbar}>
      <span className={styled.rbc_btn_group}>
        <span
          className={styled.button}
          type="button"
          onClick={navigate.bind(null, "PREV")}
        >
          <FontAwesomeIcon icon={faChevronLeft} className={styled.buttonPrev} />
        </span>
        <span className={styled.rbc_toolbar_label}>
          {`${date.getMonth() + 1}월`}
        </span>
        <span
          className={styled.button}
          type="button"
          onClick={navigate.bind(null, "NEXT")}
        >
          <FontAwesomeIcon
            icon={faChevronRight}
            className={styled.buttonNext}
          />
        </span>
      </span>
      <span>
        <button onClick={onViews.bind(null, "month")}>
          <FontAwesomeIcon className={styled.goMonth} icon={faCalendarDays} />
        </button>
      </span>
    </div>
  );
}
