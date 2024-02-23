import React from "react";
import styled from "./Student.module.css";
import useModal from "../../hooks/useModal";
import PwdRemoveBtn from "./PwdRemoveBtn";
import DoDisturbOnIcon from "@mui/icons-material/DoDisturbOn";
import { SvgIcon } from "@material-ui/core";
import EditIcon from "@mui/icons-material/Edit";
import { useMutation } from "@tanstack/react-query";

const Student = ({ item, onClick, handleRemove, handleEdit, showRemove }) => {
  /** 학생 비밀번호 초기화 쿼리 */
  const resetPwdQuery = useMutation({
    mutationKey: ["resetPwd"],
    mutationFn: () => resetPwdApi(),
    onSuccess: (res) => {
      console.log(res.data);
    },
    onError: (error) => {
      console.log(error);
    },
  });

  /** 학생 비밀번호 초기화 **/
  const resetPwdApi = (e) => {
    e.preventDefault();
    resetPwdQuery.mutate();
  };

  // /** 학생 제거 쿼리 */
  // const deleteStudentQuery = useMutation({
  //   mutationKey: ['deleteStudent'],
  //   mutationFn: () => deleteStudentApi(item.no),
  //   onSuccess: (res) => {
  //     console.log(res.data);
  //   },
  //   onError: (error) => {
  //     console.log(error);
  //   },
  // });

  /** 학생 비밀번호 초기화 **/
  const onRemove = () => {
    handleRemove(item.no);
    console.log(item);
    // deleteStudentQuery.mutate(item.no,mainClass.no)
  };

  const onEdit = () => {
    handleEdit(item);
  };

  const { openModal } = useModal();

  return (
    <>
      <tr className={styled.table} onClick={onClick}>
        <td>{item.number}</td>
        <td>{item.name}</td>
        <td>{item.asset}</td>
        {showRemove && (
          <td className={styled.resetPwdCell} onClick={onEdit}>
            {" "}
            <PwdRemoveBtn
              onClick={() =>
                openModal({
                  type: "pwdRemove",
                  props: ["비밀번호 초기화", item],
                })
              }
            />
          </td>
        )}
        {showRemove && (
          <td onClick={onRemove}>
            <SvgIcon
              component={DoDisturbOnIcon}
              style={{ fill: "red", height: "3vh" }}
            />
          </td>
        )}
        {showRemove && (
          <td onClick={onEdit}>
            <SvgIcon
              component={EditIcon}
              onClick={() =>
                openModal({
                  type: "editStudent",
                  props: ["학생 정보 수정", item],
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
