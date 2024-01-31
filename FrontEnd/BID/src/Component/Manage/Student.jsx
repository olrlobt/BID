import React from "react";
import styled from "./Student.module.css";
import useModal from "../../hooks/useModal";
import PwdRemoveBtn from './PwdRemoveBtn';
import DoDisturbOnIcon from '@mui/icons-material/DoDisturbOn';
import { SvgIcon } from "@material-ui/core";
import EditIcon from '@mui/icons-material/Edit';

const Student = ({ item, onClick, handleRemove, handleEdit, showRemove }) => {
  const onRemove = () => {
    handleRemove(item.no);
  };
  const onEdit = () => {
    handleEdit(item);
  };
  const { openModal } = useModal();


  return (
    <>
      <tr class={styled.table} onClick={onClick}>
        <td>{item.number}</td>
        <td>{item.name}</td>
        <td>{item.asset}</td>
        {showRemove && (
          <td onClick={onEdit}>
              <PwdRemoveBtn
                onClick={() =>
                  openModal({
                    type: "pwdRemove",
                    props: ["비밀번호 초기화", item]
                  })
                }
                />
          </td>
        )}
        {showRemove && (
          <td onClick={onRemove}>
            <SvgIcon component={DoDisturbOnIcon} style={{ fill: "red", height: "3vh" }}  />
          </td>
        )}
        {showRemove && (
          <td onClick={onEdit}>
              <SvgIcon component={EditIcon}
                onClick={() =>
                  openModal({
                    type: "editStudent",
                    props: ["학생 정보 수정", item]
                  })
                }
                />
          </td>
        )}
      </tr>
    </>
  );
};

export default Student;
