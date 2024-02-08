import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDownload } from '@fortawesome/free-solid-svg-icons';
import styled from './MakeClass.module.css';
import { useCallback, useState } from 'react';

export default function MakeClass() {
  const XLSX = require('xlsx');
  const [uploadedFile, setUploadedFile] = useState(null);
  const [stuFile, setStuFile] = useState(
    uploadedFile ? uploadedFile.jsonData : []
  );

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

  return (
    <section className={styled.makeClass}>
      {console.log(stuFile)}
      <button className={styled.xlsxDownload}>
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
          <input type="number" id="year" />
          학년
        </label>
        <label htmlFor="class">
          <input type="number" id="class" className={styled.class} />반
        </label>
        <div className={styled.filebox}>
          <input
            className={styled.uploadName}
            value=""
            placeholder="첨부파일"
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
            <div className={styled.infoTitle}>
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
                value={student.생년월일}
                onChange={(e) =>
                  handleInputChange(index, '생년월일', e.target.value)
                }
              />
            </div>
          ))}
      </section>
      <button className={styled.makeBtn}>학급 생성</button>
    </section>
  );
}
