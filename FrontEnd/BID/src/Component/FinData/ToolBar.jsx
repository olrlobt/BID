import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faChevronLeft,
  faChevronRight,
} from '@fortawesome/free-solid-svg-icons';
import styled from './ToolBar.module.css';

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
      <div>
        <p>
          지출 <span></span>비드
        </p>
        <p className={styled.import}>
          수입 <span></span>비드
        </p>
      </div>
      <span className={styled.rbc_btn_group}>
        <span
          className={styled.button}
          type="button"
          onClick={navigate.bind(null, 'PREV')}
        >
          <FontAwesomeIcon icon={faChevronLeft} className={styled.buttonPrev} />
        </span>
        <span className={styled.rbc_toolbar_label}>
          {`${date.getMonth() + 1}월`}
        </span>
        <span
          className={styled.button}
          type="button"
          onClick={navigate.bind(null, 'NEXT')}
        >
          <FontAwesomeIcon
            icon={faChevronRight}
            className={styled.buttonNext}
          />
        </span>
      </span>
      <span className="rbc-btn-group">
        <button onClick={onViews.bind(null, 'month')}>월</button>
      </span>
    </div>
  );
}
