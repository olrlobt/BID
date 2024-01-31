import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronLeft, faChevronRight } from "@fortawesome/free-solid-svg-icons";

export default function Toolbar(props) {
  const { date } = props;

  const navigate = (action) => {
    props.onNavigate(action);
  };

  return (
    <div className="rbc-toolbar">
      <span className="rbc-btn-group">
        <span type="button" onClick={navigate.bind(null, 'PREV')}>
          <FontAwesomeIcon icon={faChevronLeft} />
        </span>
        <span className="rbc-toolbar-label" style={{ fontSize: '2rem', fontWeight:'bold',color: '#5FA1C4' }}>
          {`${date.getMonth() + 1}월`}
        </span>
        <span type="button" onClick={navigate.bind(null, 'NEXT')}>
          <FontAwesomeIcon icon={faChevronRight} style={{color:'A6A6A6'}} />
        </span>
      </span>
    </div>
  );
}
