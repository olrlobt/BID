import React, { useEffect } from 'react';
import styled from './MakeClass.module.css';
import Back from '../../Asset/Image/SeatGame/back_btn.png';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDownload } from '@fortawesome/free-solid-svg-icons';
import { useCallback, useState } from 'react';
import { useMutation } from '@tanstack/react-query';
import { AddClass } from '../../Apis/ClassManageApis';
import { useNavigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { userSelector } from '../../Store/userSlice';
import alertBtn from '../../Component/Common/Alert';

export default function MakeClass() {
  const XLSX = require('xlsx');
  const [uploadedFile, setUploadedFile] = useState(null);
  const [stuFile, setStuFile] = useState(
    uploadedFile ? uploadedFile.jsonData : []
  );
  const [studentFormat, setStudentFormat] = useState([]);
  const [year, setYear] = useState(0);
  const [classRoom, setClassRoom] = useState(0);
  const [classInfo, setClassInfo] = useState({
    schoolCode: 'AAA',
    year: year,
    classRoom: classRoom,
    userNo: 36,
    schoolNo: 1,
    studentListSaveRequests: [],
  });

  const handleDownload = () => {
    const url = '/Student.xlsx';
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'Student.xlsx');
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  const navigate = useNavigate();
  const classSelector = useSelector(userSelector);
  const { userNo, schoolNo, schoolCode } = classSelector.adminInfo;

  const handleDrop = useCallback(async (acceptedFiles) => {
    if (acceptedFiles.length > 0) {
      const file = acceptedFiles[0];

      const reader = new FileReader();
      reader.onload = async (e) => {
        const data = new Uint8Array(e.target.result);
        const workbook = XLSX.read(data, { type: 'array', bookVBA: true });
        const sheetName = workbook.SheetNames[0];
        const sheet = workbook.Sheets[sheetName];
        const jsonData = XLSX.utils.sheet_to_json(sheet);

        setUploadedFile({ file, jsonData });
        setStuFile(jsonData);
      };

      reader.readAsArrayBuffer(file);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const handleInputChange = (index, field, value) => {
    const updatedStuFile = [...stuFile];
    updatedStuFile[index][field] = value;
    setStuFile(updatedStuFile);
  };

  const handleChange = (event, what) => {
    if (what === 'year') {
      setYear(event.target.value);
    } else if (what === 'classRoom') {
      setClassRoom(event.target.value);
    }
  };

  const handleSubmit = () => {
    setClassInfo({
      schoolCode: schoolCode,
      year: year,
      classRoom: classRoom,
      userNo: userNo,
      schoolNo: schoolNo,
      studentListSaveRequests: studentFormat,
    });
    makingClass.mutate();
  };

  function excelSerialDateToJSDate(serial) {
    var utc_days = Math.floor(serial - 25569);
    var utc_value = utc_days * 86400;
    let date = new Date(utc_value * 1000).toISOString().slice(0, 10);
    return date
  }

  useEffect(() => {
    setStudentFormat(
        stuFile.map((student) => {
          const birthDate = excelSerialDateToJSDate(student.생년월일);
          const birthDateString = birthDate.substring(2).replace(/-/g, '');
          return {
            password: birthDateString,
            name: student.이름,
            birthDate: birthDateString,
            number: student.번호,
          };
        })
    );
  }, [stuFile, classInfo, year, classRoom]);

  const makingClass = useMutation({
    mutationKey: ['makingClass'],
    mutationFn: () =>
      AddClass({ classInfo })
        .then(() => {
          alertBtn({
            text: '추가되었습니다.',
            confirmColor: '#ffd43a',
            icon: 'success',
          });
        })
        .catch(() => {
          alertBtn({
            text: '추가가 되지 않았습니다.',
            confirmColor: '#E81818',
            icon: 'error',
          });
        })
        .finally(() => {
          navigate(`/classlist/${userNo}`);
        }),
  });

  return (
    <section>
      <section className={styled.makeClass}>
        <button className={styled.xlsxDownload} onClick={handleDownload}>
          <FontAwesomeIcon icon={faDownload} />
          엑셀 파일 다운로드
        </button>
        <div className={styled.makeInfo}>
          <span>
            엑셀 파일을 다운로드 받은 다음, 양식에 맞게 학생 정보를 입력해주세요
          </span>
          <span className={styled.info2}>
            완성된 엑셀 파일을 업로드 하면 학생 정보가 자동으로 등록돼요!
          </span>
        </div>
        <section className={styled.uploadedInfo}>
          <label htmlFor="year">
            <input
              type="number"
              id="year"
              value={year}
              onChange={(e) => handleChange(e, 'year')}
            />
            학년
          </label>
          <label htmlFor="class">
            <input
              type="number"
              id="class"
              className={styled.class}
              value={classRoom}
              onChange={(e) => handleChange(e, 'classRoom')}
            />
            반
          </label>
          <div className={styled.filebox}>
            <input
              className={styled.uploadName}
              value=""
              placeholder="첨부파일"
              readOnly
            />
            <label htmlFor="file" className={styled.file}>
              파일 찾기
            </label>
            <input
              id="file"
              className={styled.inputFile}
              type="file"
              accept=".xlsx, .xls, .csv"
              onChange={(e) => handleDrop(e.target.files)}
            />
          </div>
        </section>
        <section className={styled.viewStu}>
          <div className={styled.infoTitle}>
            <span className={styled.num}>번호</span>
            <span className={styled.name}>이름</span>
            <span className={styled.birth}>생년월일</span>
          </div>
          {uploadedFile &&
            uploadedFile.jsonData.map((student, index) => (
              <div className={styled.infoTitle} key={index}>
                <input
                  className={`${styled.num} ${styled.newStu}`}
                  value={student.번호}
                  onChange={(e) =>
                    handleInputChange(index, '번호', e.target.value)
                  }
                />
                <input
                  className={`${styled.name} ${styled.newStu}`}
                  value={student.이름}
                  onChange={(e) =>
                    handleInputChange(index, '이름', e.target.value)
                  }
                />
                <input
                  className={`${styled.birth} ${styled.newStu}`}
                  value={excelSerialDateToJSDate(student.생년월일)}
                  onChange={(e) =>
                    handleInputChange(index, '생년월일', e.target.value)
                  }
                />
              </div>
            ))}
        </section>
        <button
          className={styled.makeBtn}
          onClick={() => {
            if (year === 0 || classRoom === 0) {
              alertBtn({
                title: '학년이나 반에 0은 입력할 수 없습니다.',
                confirmColor: '#E81818',
                icon: 'warning',
              });
              return;
            }
            handleSubmit();
          }}
        >
          학급 생성
        </button>
      </section>
      <img
        className={styled.back}
        src={Back}
        alt="뒤로가기"
        onClick={() => navigate(`/classlist/${userNo}`)}
        onError={(e) => e.target.src='https://media.tarkett-image.com/large/TH_PROTECTWALL_Tisse_Light_Grey.jpg'}
      />
    </section>
  );
}
